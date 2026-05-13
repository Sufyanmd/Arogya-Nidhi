package com.example.arogyanidhi.domain.repository

import com.example.arogyanidhi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun forgotPassword(email: String): Result<Unit>
}
