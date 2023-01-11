package com.prvt.horariosbuap.model

data class HorarioUsuario(
    val nombre : String = "",
    var materiasUnicas : ArrayList<Materias> = ArrayList(),
    var materiasHorarios : ArrayList<MateriasHorario> = ArrayList()
)
