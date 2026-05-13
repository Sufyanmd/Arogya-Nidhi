package com.example.arogyanidhi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.arogyanidhi.ui.auth.AuthViewModel
import com.example.arogyanidhi.ui.auth.LoginScreen
import com.example.arogyanidhi.ui.auth.RegisterScreen
import com.example.arogyanidhi.ui.dashboard.DashboardScreen
import com.example.arogyanidhi.ui.dashboard.DashboardViewModel
import com.example.arogyanidhi.ui.eligibility.EligibilityScreen
import com.example.arogyanidhi.ui.eligibility.EligibilityViewModel
import com.example.arogyanidhi.ui.hospitals.HospitalListScreen
import com.example.arogyanidhi.ui.hospitals.HospitalViewModel
import com.example.arogyanidhi.ui.profile.ProfileScreen
import com.example.arogyanidhi.ui.profile.ProfileViewModel
import com.example.arogyanidhi.ui.settings.SettingsScreen
import com.example.arogyanidhi.ui.settings.SettingsViewModel
import com.example.arogyanidhi.ui.schemes.SchemeDetailScreen
import com.example.arogyanidhi.ui.schemes.SchemeDetailViewModel
import com.example.arogyanidhi.ui.schemes.SchemeListScreen
import com.example.arogyanidhi.ui.schemes.SchemeViewModel
import com.example.arogyanidhi.ui.onboarding.OnboardingScreen
import com.example.arogyanidhi.ui.onboarding.OnboardingViewModel
import com.example.arogyanidhi.ui.splash.SplashViewModel
import com.example.arogyanidhi.ui.navigation.Screen
import com.example.arogyanidhi.ui.theme.ArogyaNidhiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArogyaNidhiTheme {
                ArogyaNidhiMain()
            }
        }
    }
}

@Composable
fun ArogyaNidhiMain() {
    val navController = rememberNavController()
    val splashViewModel: SplashViewModel = hiltViewModel()
    val startDestination by splashViewModel.startDestination.collectAsState()
    val authViewModel: AuthViewModel = hiltViewModel()

    if (startDestination == null) {
        // Show splash screen or loading
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(navController = navController, startDestination = startDestination!!) {
        composable<Screen.Onboarding> {
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                onContinue = {
                    onboardingViewModel.completeOnboarding()
                    navController.navigate(Screen.Login) {
                        popUpTo(Screen.Onboarding) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.Login> {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register) },
                onLoginSuccess = { navController.navigate(Screen.Dashboard) {
                    popUpTo(Screen.Login) { inclusive = true }
                } }
            )
        }
        composable<Screen.Register> {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login) },
                onRegisterSuccess = { navController.navigate(Screen.Dashboard) {
                    popUpTo(Screen.Register) { inclusive = true }
                } }
            )
        }
        composable<Screen.Dashboard> {
            val dashboardViewModel: DashboardViewModel = hiltViewModel()
            DashboardScreen(
                viewModel = dashboardViewModel,
                onNavigateToEligibility = { navController.navigate(Screen.EligibilityChecker) },
                onNavigateToSchemes = { navController.navigate(Screen.Schemes) },
                onNavigateToHospitals = { navController.navigate(Screen.Hospitals) },
                onNavigateToProfile = { navController.navigate(Screen.Profile) },
                onNavigateToSettings = { navController.navigate(Screen.Settings) }
            )
        }
        composable<Screen.Settings> {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.EligibilityChecker> {
            val eligibilityViewModel: EligibilityViewModel = hiltViewModel()
            EligibilityScreen(
                viewModel = eligibilityViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Schemes> {
            val schemeViewModel: SchemeViewModel = hiltViewModel()
            SchemeListScreen(
                viewModel = schemeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onSchemeClick = { schemeId ->
                    navController.navigate(Screen.SchemeDetail(schemeId))
                }
            )
        }
        composable<Screen.SchemeDetail> { backStackEntry ->
            val route: Screen.SchemeDetail = backStackEntry.toRoute()
            val schemeDetailViewModel: SchemeDetailViewModel = hiltViewModel()
            SchemeDetailScreen(
                schemeId = route.schemeId,
                viewModel = schemeDetailViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Hospitals> {
            val hospitalViewModel: HospitalViewModel = hiltViewModel()
            HospitalListScreen(
                viewModel = hospitalViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Profile> {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}