package com.example.horariosbuap.ui.theme.dataBase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class DatosViewModel : ViewModel() {
    var state : List<MateriaTabla> = emptyList()

    var profesoresState = mutableStateOf(false) //Detecta si ya esta lleno la lista de profesores
    var profesores: ArrayList<Profesores?> = ArrayList()

    var edificios : ArrayList<Edificios?> = ArrayList()

    var edificiosState = mutableStateOf(false) //Detecta si ya esta lleno la lista de edificios
    val tipoBusqueda = mutableStateOf(0)

    fun llenarProfesores(value : ArrayList<Profesores?>){
        profesores = value
        profesoresState.value = !profesores.isEmpty()
    }

    fun llenarEdificios(value: ArrayList<Edificios?>){
        edificios = value
        edificiosState.value = !edificios.isEmpty()
    }
}