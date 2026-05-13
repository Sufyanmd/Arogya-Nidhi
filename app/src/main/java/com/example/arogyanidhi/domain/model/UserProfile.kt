package com.example.arogyanidhi.domain.model

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val age: Int = 0,
    val district: String = "",
    val gender: String = "",
    val bloodGroup: String = "",
    val phone: String = ""
)