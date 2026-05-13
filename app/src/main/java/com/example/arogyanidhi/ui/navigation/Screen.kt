package com.example.arogyanidhi.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Splash : Screen()
    
    @Serializable
    object Onboarding : Screen()
    
    @Serializable
    object Login : Screen()
    
    @Serializable
    object Register : Screen()
    
    @Serializable
    object Dashboard : Screen()
    
    @Serializable
    object EligibilityChecker : Screen()
    
    @Serializable
    object Schemes : Screen()
    
    @Serializable
    data class SchemeDetail(val schemeId: String) : Screen()
    
    @Serializable
    object Hospitals : Screen()
    
    @Serializable
    object Profile : Screen()
    
    @Serializable
    object Settings : Screen()
}
