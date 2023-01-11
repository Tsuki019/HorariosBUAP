package com.prvt.horariosbuap.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.prvt.horariosbuap.model.*
import com.prvt.horariosbuap.ui.theme.dataBase.getMateriasHorario
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var periodoActual = mutableStateOf("")
    val periodoState = mutableStateOf(false)
    var materias = mutableStateListOf<Materias?>()
    var materiasBackUp : List<Materias?> = listOf() // Contiene los datos de todas las materias mientras este aplicado un filtro
    var materiasHorario : ArrayList<MateriasHorario?> = ArrayList()
    val materiasHorarioState = mutableStateOf(false) //Detecta si ya esta llenq la lista de materias por horario
    val materiasState = mutableStateOf(false) //Detecta si ya esta llena la lista de materias
    var resultMaterias : ArrayList<Materias?> = ArrayList()

    val busquedaState = mutableStateOf(false)
    val tipoBusqueda = mutableStateOf(0)

    var noticias : ArrayList<News?> = ArrayList()
    val isNewsFill = mutableStateOf(false)

    val salirAplicacion = mutableStateOf(false)     //Activador de la alerta "salir de la aplicacion"

    fun setPeriodoActual(periodo: String){
        periodoActual.value = periodo
        periodoState.value = periodoActual.value.isNotEmpty()
    }

    fun llenarProfesores(value : ArrayList<Profesores?>){
        profesores = value
        profesoresState.value = !profesores.isEmpty()
    }

    fun llenarEdificios(value: ArrayList<Edificios?>){
        edificios = value
        edificiosState.value = !edificios.isEmpty()
    }

    fun llenarMaterias(value: SnapshotStateList<Materias?>){
        materias = value
        materiasState.value = !materias.isEmpty()
        if (materiasState.value){
            materiasBackUp = materias.toList()
        }
    }

    fun llenarMateriasHorario(value: ArrayList<MateriasHorario?>){
        materiasHorario = value
        materiasHorarioState.value = materiasHorario.isNotEmpty()
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

    fun buscarHorarioMateria(
        nrc: String,
    ) : ArrayList<MateriasHorario>{
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

    fun aplicarFiltro(
        carrera: String,
        periodo: String
    ){

        materias.clear()
        if (carrera == "Todos" && periodo == "Todos"){
            materiasBackUp.toCollection(materias)

        }else if (carrera == "Todos"){ //Filtrar por periodo
            materiasBackUp.filterTo(materias) { materias -> materias?.periodo?.lowercase()!!.contains(periodo.lowercase())}

        }else if(periodo == "Todos"){   //Filtrar por carrera
            materiasBackUp.filterTo(materias) { materias -> materias?.carrera == carrera }

        }else{  //Filtrar por periodo y carrera
            materiasBackUp.filterTo(materias) { materias -> materias?.periodo?.lowercase()!!.contains(periodo.lowercase())}
            materias.removeAll { materias -> materias?.carrera != carrera  }

        }
    }
}