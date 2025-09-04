package com.example.menuactions.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class Anime (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val urlImage: String,
    val totalCaps: Int,
    val type: String
)