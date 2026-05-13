package com.example.arogyanidhi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val schemeId: String,
    val name: String,
    val isReady: Boolean = false
)
