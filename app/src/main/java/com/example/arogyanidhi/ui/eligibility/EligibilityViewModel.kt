package com.example.arogyanidhi.ui.eligibility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.domain.model.EligibilityData
import com.example.arogyanidhi.domain.model.Scheme
import com.example.arogyanidhi.domain.repository.UserRepository // Add this import
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EligibilityViewModel @Inject constructor(
    private val userRepository: UserRepository // STEP 1: Inject the repository
) : ViewModel() {

    private val _eligibilityData = MutableStateFlow(EligibilityData())
    val eligibilityData: StateFlow<EligibilityData> = _eligibilityData.asStateFlow()

    private val _eligibleSchemes = MutableStateFlow<List<Scheme>>(emptyList())
    val eligibleSchemes: StateFlow<List<Scheme>> = _eligibleSchemes.asStateFlow()

    // STEP 2: Observe the User Profile (to get Age/Gender automatically)
    private val _userProfile = MutableStateFlow<com.example.arogyanidhi.domain.model.UserProfile?>(null)

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            if (uid.isNotEmpty()) {
                userRepository.getUserProfile(uid).collect { profile ->
                    _userProfile.value = profile
                }
            }
        }
    }

    // --- Keep your update functions exactly as they are ---
    fun updateIncome(income: Double) {
        _eligibilityData.value = _eligibilityData.value.copy(income = income)
    }

    fun updateOccupation(occupation: String) {
        _eligibilityData.value = _eligibilityData.value.copy(occupation = occupation)
    }

    fun updateBpl(isBpl: Boolean) {
        _eligibilityData.value = _eligibilityData.value.copy(isBpl = isBpl)
    }

    fun updateFamilySize(size: Int) {
        _eligibilityData.value = _eligibilityData.value.copy(familySize = size)
    }

    // STEP 3: Replace the hardcoded logic with the Dynamic Filter
    fun checkEligibility(allSchemes: List<Scheme>) {
        viewModelScope.launch {
            val data = _eligibilityData.value
            val profile = _userProfile.value

            val filtered = allSchemes.filter { scheme ->
                // Check Age
                val ageOk = if (profile != null) profile.age in scheme.minAge..scheme.maxAge else true

                // Check Gender
                val genderOk = if (profile != null && scheme.genderTarget != "All") {
                    profile.gender.equals(scheme.genderTarget, ignoreCase = true)
                } else true

                // Check Income
                val incomeOk = data.income <= scheme.incomeLimit

                // Check BPL Status
                val bplOk = if (scheme.requiresBPL) data.isBpl else true

                // Check Occupation (matching scheme category)
                val occupationOk = scheme.category == "General" ||
                        scheme.category.contains(data.occupation, ignoreCase = true)

                ageOk && genderOk && incomeOk && bplOk && occupationOk
            }

            _eligibleSchemes.value = filtered
        }
    }
}