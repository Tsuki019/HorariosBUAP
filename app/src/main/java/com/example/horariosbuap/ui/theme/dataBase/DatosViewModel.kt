package com.example.horariosbuap.ui.theme.dataBase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class DatosViewModel : ViewModel() {
    var state : List<MateriaTabla> = emptyList()
    var profesoresState = mutableStateOf(false)

    var profesores: ArrayList<Profesores?> = ArrayList()

    fun llenarProfesores(value : ArrayList<Profesores?>){
        profesores = value
        println("~~~~~~~${profesores[0]!!.nombre}~~~~~~~")
        profesoresState.value = !profesores.isEmpty()
    }
}