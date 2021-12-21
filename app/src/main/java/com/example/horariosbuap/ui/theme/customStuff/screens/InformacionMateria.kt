package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.horariosbuap.ui.theme.dataBase.DatosViewModel
import com.example.horariosbuap.ui.theme.dataBase.Materias
import com.example.horariosbuap.ui.theme.dataBase.MateriasHorario
import com.example.horariosbuap.ui.theme.dataBase.getMateriasHorario

@Composable
fun InformacionMateria(
    nrc : String?,
    datosViewModel: DatosViewModel,
    onBack : () -> Unit
) {


    val materiaInfo = infoMateria(datosViewModel = datosViewModel, nrc = nrc!!)
    val materiasHorario = horarioMateria(datosViewModel = datosViewModel, nrc)


    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = Color.Black)
            }
            Text(text = materiaInfo.nrc)
            for (materia in materiasHorario){
                Text(text = materia.dia)
                Text(text = materia.salon)
            }
        }
    }
}

fun infoMateria(
    datosViewModel: DatosViewModel,
    nrc: String
) : Materias{

    for (materia in datosViewModel.materias){
        if (materia!!.nrc == nrc){
            return materia
        }
    }

    return Materias()
}

fun horarioMateria(
    datosViewModel: DatosViewModel,
    nrc: String
) : ArrayList<MateriasHorario>{
    val materiasList : ArrayList<MateriasHorario> = ArrayList()

    if (!datosViewModel.materiasHorarioState.value){
        getMateriasHorario(datosViewModel = datosViewModel)
    }else{
        for (materia in datosViewModel.materiasHorario){
            if (materia!!.nrc == nrc){
                materiasList.add(materia)
            }
        }
        return materiasList
    }

    return materiasList
}