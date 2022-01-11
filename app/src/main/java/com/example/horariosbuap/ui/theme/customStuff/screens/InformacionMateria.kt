package com.example.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.OutlinedMediaButton
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.ui.theme.dataBase.getMateriasHorario
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.horariosbuap.ui.theme.customStuff.components.AlertaConformacion

private val materiaElegida = mutableStateOf(Materias())

@Composable
fun InformacionMateria(
    nrc : String?,
    datosViewModel: DatosViewModel,
    onBack : () -> Unit,
    nombreHorario : String,
    userDataViewModel: UserDataViewModel,
    activity : Activity,
) {

    val userId =  FirebaseAuth.getInstance().currentUser!!.uid
    val materiaInfo = InfoMateria(datosViewModel = datosViewModel, nrc = nrc!!)
    val materiasHorario = datosViewModel.buscarHorarioMateria(nrc = nrc)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blanco_fondo))
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
//            IconButton(onClick = { onBack() }) {
//                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = Color.Black)
//            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    text = "Datos Principales",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            item { Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 8.dp),
                color = colorResource(id = R.color.azulOscuroInstitucional)) }
            item { TablaPrincipal(materia = materiaInfo) }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    text = "Horario por semana",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            item { Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 8.dp),
                color = colorResource(id = R.color.azulOscuroInstitucional)) }
            item { TablasHorario(materiaHorario = materiasHorario) }
            item {
                if (nombreHorario != " "){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 6.dp)
                            .wrapContentSize(Alignment.Center)
                    ) {
                        OutlinedMediaButton(
                            text = "Agregar a Mi Horario",
                            onClick = { materiaElegida.value= materiaInfo },
                            buttonColor = colorResource(id = R.color.azulOscuroInstitucional),
                            width = 170.dp,
                            heigth = 40.dp,
                            textStyle = TextStyle(
                                fontSize= 15.sp,
                                color = colorResource(id = R.color.azulOscuroInstitucional),
                                fontFamily = FontFamily(Font(R.font.source_sans_pro))
                            )
                        )
                    }
                }
            }
        }
        if (materiaElegida.value.nombre != ""){
            AlertaConformacion(
                nombreHorario = nombreHorario,
                userId = userId,
                userDataViewModel = userDataViewModel,
                materiaElegida = materiaElegida,
                activity = activity,
                datosViewModel = datosViewModel
            )
        }
    }
}
@Composable
private fun TablaPrincipal(
    materia: Materias
){
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val datos = listOf("NRC", "Sección", "Clave")
    
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp),
//               contentPadding = rememberInsetsPaddingValues(
//                   insets = LocalWindowInsets.current.systemBars,
//                   applyTop = false)
//    ){
//        item {
            Row(
                modifier = Modifier.background(color = azulOscuro.copy(alpha = 0.8f))
            ) {
                TableCell(text = "Materia", weight = 1f, textColor = Color.White, textSize = 17.sp, fontWeight = FontWeight.Bold)
            }
            Row(
                
            ) {
                TableCell(text = materia.nombre, weight = 1f, textColor = azulClaro, textSize = 15.sp, fontWeight = FontWeight.Bold)
            }
//        }
//        item {
            Row(
                modifier = Modifier.background(color = azulOscuro.copy(alpha = 0.8f))
            ) {
                TableCell(text = "Docente", weight = 1f, textColor = Color.White, textSize = 17.sp, fontWeight = FontWeight.Bold)
            }
            Row{
                TableCell(text = materia.profesor, weight = 1f, textColor = azulClaro, textSize = 15.sp, fontWeight = FontWeight.Bold)
            }
//        }
//        item {
            Row(
                modifier = Modifier.background(color = azulClaro)
            ) {
                for (item in datos)
                    TableCell(text = item, weight = 0.33f, textColor = Color.White, textSize = 14.sp, fontWeight = FontWeight.Bold)
            }
//        }
//        item {
            Row() {
                TableCell(text = materia.nrc, weight = 0.33f, textColor = azulClaro, textSize = 15.sp, fontWeight = FontWeight.Bold)
                TableCell(text = materia.secc, weight = 0.33f, textColor = azulClaro, textSize = 15.sp, fontWeight = FontWeight.Bold)
                TableCell(text = materia.clave, weight = 0.33f, textColor = azulClaro, textSize = 15.sp, fontWeight = FontWeight.Bold)

            }
//        }
//    }
}

@Composable
private fun TablasHorario(
    materiaHorario: ArrayList<MateriasHorario>
) {
    for (item in materiaHorario) {
        CrearTablaDia(datosMateria = item)
        Divider(modifier = Modifier.padding(vertical = 5.dp), color = Color.Transparent)
    }
}


@Composable
private fun CrearTablaDia(
    datosMateria : MateriasHorario
) {
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val encabezados = listOf("Entrada", "Salida", "Salon", "Edificio")
    val weight = 0.25f
    var dia = ""

    when(datosMateria.dia){
        "L" -> dia = "Lunes"
        "A" -> dia = "Martes"
        "M" -> dia = "Miercoles"
        "J" -> dia = "Jueves"
        "V" -> dia = "Viernes"
        "S" -> dia = "Sabado"
    }

    Row(
        modifier = Modifier.background(color = azulOscuro.copy(alpha = 0.8f))
    ) {
        TableCell(text = dia, weight = 1f, textColor = Color.White, textSize = 17.sp, fontWeight = FontWeight.Bold)
    }
    Row(
        modifier = Modifier.background(color = azulClaro)
    ) {
        for (item in encabezados)
            TableCell(text = item, weight = weight, textColor = Color.White, textSize = 14.sp, fontWeight = FontWeight.Bold)
    }
    Row {
        TableCell(text = datosMateria.entrada, weight = weight, textColor = azulClaro, textSize = 14.sp)
        TableCell(text = datosMateria.salida, weight = weight, textColor = azulClaro, textSize = 14.sp)
        TableCell(text = datosMateria.salon, weight = weight, textColor = azulClaro, textSize = 14.sp)
        TableCell(text = datosMateria.edificio, weight = weight, textColor = azulClaro, textSize = 14.sp)
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor : Color,
    textSize : TextUnit,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, color = colorResource(id = R.color.azulOscuroInstitucional))
            .weight(weight)
            .padding(8.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = textColor,
            fontSize = textSize,
            fontFamily = FontFamily(Font(R.font.source_sans_pro)),
            fontWeight = fontWeight
        )
    )
}

private fun InfoMateria(
    datosViewModel: DatosViewModel,
    nrc: String
) : Materias {

    for (materia in datosViewModel.materias){
        if (materia!!.nrc == nrc){
            return materia
        }
    }

    return Materias()
}

