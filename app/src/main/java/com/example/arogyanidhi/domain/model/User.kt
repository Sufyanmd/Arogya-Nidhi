package com.example.arogyanidhi.domain.model

data class User(
    val uid: String,
    val email: String?,
    val displayName: String? = null
)
