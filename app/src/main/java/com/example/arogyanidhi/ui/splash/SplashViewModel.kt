package com.example.arogyanidhi.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.data.local.PreferenceManager
import com.example.arogyanidhi.domain.repository.AuthRepository
import com.example.arogyanidhi.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination: StateFlow<Any?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val isOnboardingCompleted = preferenceManager.isOnboardingCompleted.first()
            if (!isOnboardingCompleted) {
                _startDestination.value = Screen.Onboarding
            } else {
                val user = authRepository.currentUser.first()
                _startDestination.value = if (user != null) Screen.Dashboard else Screen.Login
            }
        }
    }
}
