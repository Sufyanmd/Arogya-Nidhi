package com.example.arogyanidhi.ui.hospitals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.domain.model.Hospital
import com.example.arogyanidhi.util.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val locationHelper: LocationHelper
) : ViewModel() {
    
    private val allHospitals = listOf(
        // Govt Hospitals
        Hospital(id = "G1", name = "Victoria Hospital", district = "Bangalore Urban", address = "Fort Road, near City Market", contact = "080-26701150", type = "Govt", latitude = 12.9634, longitude = 77.5756),
        Hospital(id = "G2", name = "Bowring and Lady Curzon Hospital", district = "Bangalore Urban", address = "Shivajinagar, Bangalore", contact = "080-25591362", type = "Govt", latitude = 12.9837, longitude = 77.6015),
        Hospital(id = "G3", name = "District Hospital, Ramanagara", district = "Ramanagara", address = "B.M Road, Ramanagara", contact = "080-27271224", type = "Govt", latitude = 12.7230, longitude = 77.2764),
        Hospital(id = "G4", name = "K.C. General Hospital", district = "Bangalore Urban", address = "Malleswaram, Bangalore", contact = "080-23344483", type = "Govt", latitude = 12.9922, longitude = 77.5714),
        Hospital(id = "G5", name = "General Hospital, Tumakuru", district = "Tumakuru", address = "B.H. Road, Tumakuru", contact = "0816-2278455", type = "Govt", latitude = 13.3379, longitude = 77.1006),

        // Private Hospitals
        Hospital(id = "P1", name = "Apollo Hospital", district = "Bangalore Urban", address = "154, IIM, Bannerghatta Main Rd", contact = "080-26304050", type = "Private", latitude = 12.8953, longitude = 77.6010),
        Hospital(id = "P2", name = "Fortis Hospital", district = "Bangalore Urban", address = "154/9, Bannerghatta Main Rd", contact = "080-66214444", type = "Private", latitude = 12.8943, longitude = 77.5990),
        Hospital(id = "P3", name = "Manipal Hospital", district = "Bangalore Urban", address = "98, Old Airport Road", contact = "080-25024444", type = "Private", latitude = 12.9593, longitude = 77.6444),
        Hospital(id = "P4", name = "Columbia Asia Referral Hospital", district = "Bangalore Urban", address = "Yeshwanthpur, Bangalore", contact = "080-39898969", type = "Private", latitude = 13.0130, longitude = 77.5516),
        Hospital(id = "P5", name = "Narayana Health City", district = "Bangalore Rural", address = "Bommasandra Industrial Area", contact = "080-71222222", type = "Private", latitude = 12.8105, longitude = 77.6936),
        Hospital(id = "P6", name = "Aster CMI Hospital", district = "Bangalore Urban", address = "Sahakar Nagar, Hebbal", contact = "080-43420100", type = "Private", latitude = 13.0534, longitude = 77.5929),
        Hospital(id = "P7", name = "Sakra World Hospital", district = "Bangalore Urban", address = "Outer Ring Rd, Marathahalli", contact = "080-49694969", type = "Private", latitude = 12.9304, longitude = 77.6881),

        // Clinics
        Hospital(id = "C1", name = "Practo Care Surgeries", district = "Bangalore Urban", address = "HSR Layout, Bangalore", contact = "080-47101234", type = "Clinic", latitude = 12.9121, longitude = 77.6445),
        Hospital(id = "C2", name = "Cloudnine Clinic", district = "Bangalore Urban", address = "Jayanagar, Bangalore", contact = "1860-500-9999", type = "Clinic", latitude = 12.9250, longitude = 77.5897),
        Hospital(id = "C3", name = "Apollo Clinic", district = "Bangalore Urban", address = "Indiranagar, Bangalore", contact = "080-41261111", type = "Clinic", latitude = 12.9784, longitude = 77.6408),
        Hospital(id = "C4", name = "Vasan Eye Care", district = "Bangalore Urban", address = "Koramangala, Bangalore", contact = "080-39890000", type = "Clinic", latitude = 12.9352, longitude = 77.6245),
        Hospital(id = "C5", name = "Dr. Batra’s Homeopathy", district = "Bangalore Urban", address = "M.G. Road, Bangalore", contact = "080-25553333", type = "Clinic", latitude = 12.9750, longitude = 77.6070)
    )

    private val _userLocation = MutableStateFlow<android.location.Location?>(null)
    private val _isLocationEnabled = MutableStateFlow(false)
    val isLocationEnabled: StateFlow<Boolean> = _isLocationEnabled.asStateFlow()
    
    private val _hospitals = MutableStateFlow<List<Hospital>>(allHospitals)
    val hospitals: StateFlow<List<Hospital>> = _hospitals.asStateFlow()

    fun toggleLocation(enabled: Boolean) {
        _isLocationEnabled.value = enabled
        if (enabled) {
            startLocationUpdates()
        } else {
            _hospitals.value = allHospitals
            _userLocation.value = null
        }
    }

    private fun startLocationUpdates() {
        viewModelScope.launch {
            locationHelper.getLocationUpdates().collect { location ->
                if (_isLocationEnabled.value) {
                    _userLocation.value = location
                    filterHospitals(location)
                }
            }
        }
    }

    private fun filterHospitals(location: android.location.Location?) {
        if (location == null || !_isLocationEnabled.value) {
            _hospitals.value = allHospitals
            return
        }

        val nearby = allHospitals.filter { hospital ->
            val distance = locationHelper.calculateDistance(
                location.latitude, location.longitude,
                hospital.latitude, hospital.longitude
            )
            distance <= 100.0
        }.sortedBy { hospital ->
            locationHelper.calculateDistance(
                location.latitude, location.longitude,
                hospital.latitude, hospital.longitude
            )
        }
        _hospitals.value = nearby
    }

    fun getDistance(hospital: Hospital): String {
        val location = _userLocation.value ?: return ""
        val distance = locationHelper.calculateDistance(
            location.latitude, location.longitude,
            hospital.latitude, hospital.longitude
        )
        return String.format("%.1f km", distance)
    }
}
