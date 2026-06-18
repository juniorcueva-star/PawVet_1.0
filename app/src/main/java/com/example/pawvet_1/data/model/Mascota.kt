package com.example.pawvet_1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val tipo: String = "Perro", // Nuevo campo: Perro, Gato, etc.
    val raza: String,
    val edad: Int,
    val peso: Double
)
