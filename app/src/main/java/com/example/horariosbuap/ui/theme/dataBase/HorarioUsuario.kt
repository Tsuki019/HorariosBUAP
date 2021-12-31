package com.example.horariosbuap.ui.theme.dataBase

data class HorarioUsuario(
    val nombre : String = "",
    var materiasUnicas : ArrayList<Materias> = ArrayList(),
    var materiasHorarios : ArrayList<MateriasHorario> = ArrayList()
)
