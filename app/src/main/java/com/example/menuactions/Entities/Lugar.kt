package com.example.menuactions.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lugares")
data class Lugar(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0
)
