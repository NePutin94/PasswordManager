package com.example.passwordmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logins")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val url: String,
    val note: String,
)