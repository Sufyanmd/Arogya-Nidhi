package com.example.arogyanidhi.data.repository

import com.example.arogyanidhi.domain.model.UserProfile
import com.example.arogyanidhi.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore // This makes the Firestore import active
) : UserRepository {

    override fun getUserProfile(uid: String): Flow<UserProfile?> = callbackFlow {
        // 'firestore' here refers to the one in the constructor above
        val docRef = firestore.collection("users").document(uid)

        val registration = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                cancel("Error fetching firestore data", error) // This makes the cancel import active
                return@addSnapshotListener
            }

            val profile = snapshot?.toObject(UserProfile::class.java)
            trySend(profile)
        }

        awaitClose { registration.remove() }
    }

    override suspend fun saveUserProfile(userProfile: UserProfile): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(userProfile.uid)
                .set(userProfile)
                .await() // <-- Add this! This forces Kotlin to wait for the upload to finish
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadProfileImage(uid: String, imageBytes: ByteArray): Result<String> {
        // We will implement the actual Firebase Storage logic here later
        // For now, this just lets the app compile
        return Result.success("placeholder_url")
    }
}
