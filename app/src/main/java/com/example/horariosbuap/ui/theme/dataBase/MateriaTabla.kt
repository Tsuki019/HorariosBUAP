package com.example.horariosbuap.ui.theme.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materias")
class MateriaTabla(
    val idMateria : String,
    val profesor : String,
    val dia : String,
    val entrada : Int,
    val salida : Int,
    val edificio : String,
    val salon : String,
    @PrimaryKey(autoGenerate = true)
    val nrc : Int
){}