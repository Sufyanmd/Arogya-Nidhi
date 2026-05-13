package com.example.arogyanidhi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Scheme(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val eligibilityCriteria: String = "", // Keep this for the user to read

    // --- Add these for the App to calculate logic ---
    val minAge: Int = 0,
    val maxAge: Int = 120,
    val incomeLimit: Int = Int.MAX_VALUE,
    val genderTarget: String = "All", // "Male", "Female", "All"
    val requiresBPL: Boolean = false,
    // ------------------------------------------------

    val documentsRequired: List<String> = emptyList(),
    val benefits: String = "",
    val applicationProcess: String = "",
    val category: String = ""
)