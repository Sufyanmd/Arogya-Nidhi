package com.example.arogyanidhi.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.domain.model.UserProfile
import com.example.arogyanidhi.domain.repository.AuthRepository
import com.example.arogyanidhi.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                user?.let {
                    userRepository.getUserProfile(it.uid).collect { profile ->
                        _userProfile.value = profile
                    }
                }
            }
        }
    }
}
