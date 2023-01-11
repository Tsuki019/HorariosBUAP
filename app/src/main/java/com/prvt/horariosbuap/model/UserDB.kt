package com.prvt.horariosbuap.model

import java.util.*


data class UserDB(
    val numHorarios : Int = 0,
    val correo : String = "",
    val provider : String = "",
    val fechaCambioNombre : Date = Date(1654059600000)
)
