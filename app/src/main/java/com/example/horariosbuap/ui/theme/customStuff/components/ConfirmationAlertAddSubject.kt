package com.example.horariosbuap.ui.theme.customStuff.components

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.getMateriasHorario
import com.example.horariosbuap.ui.theme.dataBase.setMateriaHorarios
import com.example.horariosbuap.ui.theme.dataBase.setMateriaUnica
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import java.nio.file.SimpleFileVisitor

@Composable
fun AlertaConformacion(
    nombreHorario: String,
    userId  : String,
    userDataViewModel: UserDataViewModel,
    materiaElegida : MutableState<Materias>,
    activity: Activity,
    datosViewModel : DatosViewModel
) {
    val errorVisibility = remember{ mutableStateOf(false) }

    if (errorVisibility.value){
        ErrorAgregarMateria(errorVisibility = errorVisibility, materiaElegida = materiaElegida)
    }else{
        AlertDialog(
            modifier = Modifier.clip(RoundedCornerShape(8)),
            onDismissRequest = {},
            title = {
                Text(
                    text = "Confirmar materia",
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                        textAlign = TextAlign.Center
                    )
                )
            },
            text = {
                Text(
                    text = "¿Agregar '${materiaElegida.value.nombre}' con NRC: '${materiaElegida.value.nrc}' a tu horario?",
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    )
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = {
                        val horarios = datosViewModel.buscarHorarioMateria(materiaElegida.value.nrc)
                        if (userDataViewModel.revisarDisponibilidadHorarios(nrc = materiaElegida.value.nrc, nombreHorario = nombreHorario, datosViewModel = datosViewModel)){
                            setMateriaHorarios(
                                horarios = horarios,
                                nombreHorario = nombreHorario,
                                userId = userId,
                                userDataViewModel = userDataViewModel
                            )
                            setMateriaUnica(
                                materia = materiaElegida.value,
                                nombreHorario = nombreHorario,
                                userId = userId,
                                userDataViewModel = userDataViewModel,
                                activity = activity,
                            )
                        }else{
                            errorVisibility.value = true
                        }
                    })
                    {
                        Text(
                            text = "Agregar",
                            style = TextStyle(
                                color = primaryColorCustom,
                                fontSize = 20.sp,
                                fontFamily = sansPro,
                            )
                        )
                    }
                    TextButton(onClick = { materiaElegida.value = Materias() }) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                color = primaryColorCustom,
                                fontSize = 20.sp,
                                fontFamily = sansPro,
                            )
                        )
                    }
                }
            }
        )
    }

}

@Composable
private fun ErrorAgregarMateria(
    errorVisibility : MutableState<Boolean>,
    materiaElegida: MutableState<Materias>
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(8)),
        onDismissRequest = {
            errorVisibility.value = false
            materiaElegida.value = Materias() },
        title = {
            Text(
                text = "Error al agregar",
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    textAlign = TextAlign.Center
                )
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.error_match_schedule_subjects),
                style = TextStyle(
                    color = primaryColorCustom,
                    fontSize = 16.sp,
                    fontFamily = sansPro,
                )
            )
        },
        buttons = {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = {
                    errorVisibility.value = false
                    materiaElegida.value = Materias() }
                ){
                    Text(
                        text = "Aceptar",
                        style = TextStyle(
                            color = primaryColorCustom,
                            fontSize = 20.sp,
                            fontFamily = sansPro,
                        )
                    )
                }
            }
        }
    )
}