package com.example.arogyanidhi.domain.repository

import com.example.arogyanidhi.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserProfile(uid: String): Flow<UserProfile?>
    suspend fun saveUserProfile(userProfile: UserProfile): Result<Unit>
    suspend fun uploadProfileImage(uid: String, imageBytes: ByteArray): Result<String>
}
