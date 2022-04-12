package com.example.horariosbuap.model

import com.google.firebase.Timestamp
import java.util.*


data class UserDB(
    val numHorarios : Int = 0,
    val correo : String = "",
    val provider : String = "",
    val fechaCambioNombre : Date? = null
)
