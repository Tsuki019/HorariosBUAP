package com.example.horariosbuap.viewmodel

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horariosbuap.model.*
import com.example.horariosbuap.ui.theme.customStuff.BottomNavScreens
import com.example.horariosbuap.ui.theme.dataBase.getMateriasHorario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatosViewModel @Inject constructor() : ViewModel() {
    val profesoresState = mutableStateOf(false) //Detecta si ya esta lleno la lista de profesores
    var profesores: ArrayList<Profesores?> = ArrayList()
    var resultProfesores : ArrayList<Profesores?> = ArrayList()

    var edificios : ArrayList<Edificios?> = ArrayList()
    val edificiosState = mutableStateOf(false) //Detecta si ya esta lleno la lista de edificios

    var salones : ArrayList<Salones?> = ArrayList()
    val salonesState = mutableStateOf(false)
    var resultSalones : ArrayList<Salones?> = ArrayList()

    var materias : ArrayList<Materias?> = ArrayList()
    var materiasHorario : ArrayList<MateriasHorario?> = ArrayList()
    val materiasHorarioState = mutableStateOf(false) //Detecta si ya esta llenq la lista de materias por horario
    val materiasState = mutableStateOf(false) //Detecta si ya esta llena la lista de materias
    var resultMaterias : ArrayList<Materias?> = ArrayList()

    val busquedaState = mutableStateOf(false)
    val tipoBusqueda = mutableStateOf(0)

    var noticias : ArrayList<News?> = ArrayList()
    val isNewsFill = mutableStateOf(false)

    val salirAplicacion = mutableStateOf(false)     //Activador de la alerta "salir de la aplicacion"

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

    fun llenarMateriasHorario(value: ArrayList<MateriasHorario?>){
        materiasHorario = value
        materiasHorarioState.value = !materiasHorario.isEmpty()
    }

    fun buscarNombreProfesor(key : MutableState<String>){
        val list : ArrayList<Profesores?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in profesores){
                if (dato!!.nombre.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultProfesores = list
            busquedaState.value = !resultProfesores.isEmpty()
        }
    }

    fun buscarPuestoProfesor(key : MutableState<String>){
        val list : ArrayList<Profesores?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in profesores){
                if (dato!!.puesto.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultProfesores = list
            busquedaState.value = !resultProfesores.isEmpty()
        }
    }

    fun buscarSalones(key: MutableState<String>) {
        val list : ArrayList<Salones?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in salones){
                if (dato!!.data.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultSalones = list
            busquedaState.value = !resultSalones.isEmpty()
        }
    }

    fun buscarMateriaPorNombre(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in materias){
                if (dato!!.nombre.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultMaterias = list
            busquedaState.value = !resultMaterias.isEmpty()
        }

    }

    fun buscarMateriaPorProfesor(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in materias){
                if (dato!!.profesor.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultMaterias = list
            busquedaState.value = !resultMaterias.isEmpty()
        }
    }

    fun buscarMateriaPorNRC(key: MutableState<String>) {
        val list : ArrayList<Materias?> = arrayListOf()

        if (key.value.length > 1){
            for(dato in materias){
                if (dato!!.nrc.lowercase().contains(key.value.lowercase())){
                    list.add(dato)
                }
            }
            resultMaterias = list
            busquedaState.value = !resultMaterias.isEmpty()
        }
    }

    fun switchStates(){
        edificiosState.value = !edificiosState.value
        profesoresState.value = !profesoresState.value
        materiasState.value = !materiasState.value
        busquedaState.value = !busquedaState.value
    }

    fun llenarSalones(value: ArrayList<Salones?>) {
        salones = value
        salonesState.value = !salones.isEmpty()
    }

    fun buscarHorarioMateria(nrc: String) : ArrayList<MateriasHorario>{
        println("ENTRA A BUSCAR")
        val materiasList : ArrayList<MateriasHorario> = ArrayList()

        if (!materiasHorarioState.value){
            getMateriasHorario(datosViewModel = this)
        }else{
            for (materia in materiasHorario){
                if (materia!!.nrc == nrc){
                    materiasList.add(materia)
                }
            }
            println("tamano ${materiasList.size}")
            return materiasList
        }
        return materiasList
    }

    fun llenarNoticias(news : ArrayList<News?>){
        noticias = news
        isNewsFill.value = true
    }
}