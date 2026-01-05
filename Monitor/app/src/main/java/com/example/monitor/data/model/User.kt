package com.example.monitor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val defaultCurrency: String = "PLN",
    val darkMode: Boolean = false
)