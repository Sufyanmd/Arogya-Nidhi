package com.example.arogyanidhi.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.domain.model.UserProfile
import com.example.arogyanidhi.domain.repository.AuthRepository
import com.example.arogyanidhi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    fun getCurrentUserUid(): String {
        // This directly accesses the underlying Firebase User if your repository allows it
        return com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    init {
        viewModelScope.launch {
            authRepository.currentUser
                .flatMapLatest { user ->
                    if (user != null) {
                        userRepository.getUserProfile(user.uid)
                    } else {
                        flowOf(null) // If no user, return a null profile
                    }
                }
                .collect { profile ->
                    _userProfile.value = profile
                }
        }
    }

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            userRepository.saveUserProfile(profile)
        }
    }
}
