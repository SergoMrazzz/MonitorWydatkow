package com.example.monitor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryId: String,
    val name: String,
    val icon: String,
    val color: String
)