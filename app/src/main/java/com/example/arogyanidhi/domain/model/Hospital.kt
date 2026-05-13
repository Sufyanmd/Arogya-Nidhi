package com.example.arogyanidhi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Hospital(
    val id: String = "",
    val name: String = "",
    val district: String = "",
    val address: String = "",
    val contact: String = "",
    val type: String = "", // Govt/Private
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
