package com.example.horariosbuap.ui.theme.dataBase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class DatosViewModel : ViewModel() {
    var state : List<MateriaTabla> = emptyList()

    var profesoresState = mutableStateOf(false) //Detecta si ya esta lleno la lista de profesores
    var profesores: ArrayList<Profesores?> = ArrayList()
    var resultProfesores : ArrayList<Profesores?> = ArrayList()

    var edificios : ArrayList<Edificios?> = ArrayList()
    var edificiosState = mutableStateOf(false) //Detecta si ya esta lleno la lista de edificios
    var resultSalones : ArrayList<Edificios?> = ArrayList()

    var materias : ArrayList<Materias?> = ArrayList()
    var materiasState = mutableStateOf(false) //Detecta si ya esta lleno la lista de materias
    var resultMaterias : ArrayList<Materias?> = ArrayList()


    val busquedaState = mutableStateOf(false)
    val tipoBusqueda = mutableStateOf(0)

    fun llenarProfesores(value : ArrayList<Profesores?>){
        profesores = value
        profesoresState.value = !profesores.isEmpty()
    }

    fun llenarEdificios(value: ArrayList<Edificios?>){
        edificios = value
        edificiosState.value = !edificios.isEmpty()
    }

    fun llenarMaterias(value: ArrayList<Materias?>){
        materias = value
        materiasState.value = !materias.isEmpty()
    }

    fun buscarNombreProfesor(
        key : MutableState<String>
    ){
        val list : ArrayList<Profesores?> = arrayListOf()

        for(dato in profesores){
            if (dato!!.nombre.lowercase().contains(key.value.lowercase())){
                list.add(dato)
            }
        }
        resultProfesores = list
        busquedaState.value = !resultProfesores.isEmpty()
    }

    fun buscarPuestoProfesor(
        key : MutableState<String>
    ){
        val list : ArrayList<Profesores?> = arrayListOf()

        for(dato in profesores){
            if (dato!!.puesto.lowercase().contains(key.value.lowercase())){
                list.add(dato)
            }
        }
        resultProfesores = list
        busquedaState.value = !resultProfesores.isEmpty()
    }

    fun buscarPorEdificio(key: MutableState<String>) {

    }

    fun buscarNombreMateria(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        for(dato in materias){
            if (dato!!.nombre.lowercase().contains(key.value.lowercase())){
                list.add(dato)
            }
        }
        resultMaterias = list
        busquedaState.value = !resultMaterias.isEmpty()
    }

    fun buscarProfesorMateria(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        for(dato in materias){
            if (dato!!.profesor.lowercase().contains(key.value.lowercase())){
                list.add(dato)
            }
        }
        resultMaterias = list
        busquedaState.value = !resultMaterias.isEmpty()
    }

    fun buscarNRCMateria(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        for(dato in materias){
            if (dato!!.nrc.lowercase().contains(key.value.lowercase())){
                list.add(dato)
            }
        }
        resultMaterias = list
        busquedaState.value = !resultMaterias.isEmpty()
    }


}