package com.example.arogyanidhi.domain.model

data class EligibilityData(
    val income: Double = 0.0,
    val occupation: String = "",
    val isBpl: Boolean = false,
    val familySize: Int = 1,
    val age: Int = 0
)
