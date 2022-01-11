package com.example.horariosbuap.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.horariosbuap.model.HorarioUsuario
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.model.UserDB

class UserDataViewModel : ViewModel()
{
    val horarios : ArrayList<HorarioUsuario> = ArrayList()
    val isMateriasUnicasFill = mutableStateOf(false)
    val isMateriasHorarioFill = mutableStateOf(false)
    val isUserDataLoaded = mutableStateOf(false)

    var nombresHorarios : ArrayList<(String)> = ArrayList()
    var userData : MutableState<UserDB> = mutableStateOf(UserDB())

    val datosFromCache = mutableStateOf(false)

    fun fillNombresHorarios(
        nombres : ArrayList<String>
    ){
        for(nombre in nombres){
            horarios.add(HorarioUsuario(nombre = nombre))
        }
        isUserDataLoaded.value = true
    }

    fun agregarUserData(
        user : UserDB
    ){
        userData.value = user

//        if (userData.value != UserDB() ){
//            isUserDataLoaded.value = true
//        }
    }

    fun agregarMateriasUnicas(
        nombre: String,
        materias: ArrayList<Materias>
    ) {
        var band = false
        for (i in 0 until horarios.size){
            if (horarios[i].nombre == nombre && !band){
                horarios[i].materiasUnicas = materias
                band = true
            }
        }
        if (!band){
            horarios.add(HorarioUsuario(nombre = nombre, materiasUnicas = materias))
        }
        isMateriasUnicasFill.value = true
    }

    fun agregarMateriasHorario(
        nombre: String,
        materias: ArrayList<MateriasHorario>
    ) {
        var band = false
        for (i in 0 until horarios.size){
            if (horarios[i].nombre == nombre && !band){
                horarios[i].materiasHorarios = materias
                band = true
            }
        }
        if (!band){
            horarios.add(HorarioUsuario(nombre = nombre, materiasHorarios = materias))
        }
        isMateriasHorarioFill.value = true
    }

    fun agregarMateria (
        nombreHorario : String,
        materia : Materias
    ){
        for (i in 0 until horarios.size){
            if (horarios[i].nombre == nombreHorario){
                horarios[i].materiasUnicas.add(materia)
                break
            }
        }
    }

    fun agregarHorariosDeMateria(
        nombreHorario: String,
        materiaHorarios: ArrayList<MateriasHorario>
    ){
        for(i in 0 until horarios.size){
            if (horarios[i].nombre == nombreHorario){
                for(horario in materiaHorarios){
                    horarios[i].materiasHorarios.add(horario)
                }
                break
            }
        }
    }

    fun agregarNombreHorario(
        nombre: String,
    ){
        var band = false
        if (horarios.size > 0){
            for (i in 0 until horarios.size){
                if (horarios[i].nombre == nombre){
                    band = true
                }
            }
            if(!band){
                horarios.add(HorarioUsuario(nombre = nombre))
            }
        }else{
            horarios.add(HorarioUsuario(nombre = nombre))
        }
    }

    fun cargarHorario(
        id: String,
        materias: ArrayList<Materias>,
        materiasHorario: ArrayList<MateriasHorario>
    ) {
        val horario = HorarioUsuario(nombre = id, materiasUnicas = materias, materiasHorarios = materiasHorario)
        horarios.add(horario)
        isUserDataLoaded.value = true
    }

    fun eliminarHorario(
        nombre : String
    ){
        for (i in 0 until horarios.size){
            if (horarios[i].nombre == nombre){
                horarios.removeAt(i)
                break
            }
        }
    }

    fun revisarDisponibilidadHorarios(
        nrc : String,
        nombreHorario: String,
        datosViewModel: DatosViewModel
    ) : Boolean{
        var horarioLibre = true
        val materiasHorarios = datosViewModel.buscarHorarioMateria(nrc)
        val horario = horarios.find { materiasHor -> materiasHor.nombre == nombreHorario }

        if (horario!!.materiasHorarios.size > 0){
            for (horarioSolo in  horario.materiasHorarios){
                for (horarioRegistrado in  materiasHorarios){
                    if (horarioSolo.entrada == horarioRegistrado.entrada && horarioSolo.dia == horarioRegistrado.dia){
                        horarioLibre = false
                        break
                    }
                }
                if (!horarioLibre){
                    break
                }
            }
        }
        return horarioLibre
    }
}