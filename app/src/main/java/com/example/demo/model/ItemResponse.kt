package com.example.demo.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class ItemResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val image: String?,
    val category: String?,
    val description: String?,
    val price: Double?,
    @Embedded
    val rating: Rating?
)

data class Rating(
    val count: Int?,
    val rate: Double?
)
