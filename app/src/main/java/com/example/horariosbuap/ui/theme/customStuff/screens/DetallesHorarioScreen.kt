package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TabMenu
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.*
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@ExperimentalAnimationApi

@ExperimentalPagerApi
@Composable
fun DetallesHorarioScreen(
    nombreHorario :String?,
    userDataViewModel: UserDataViewModel,
    onNavToAddSubject: (String) -> Unit,
    datosViewModel : DatosViewModel
) {
    var materiasUnicas : ArrayList<Materias> =  remember { arrayListOf() }
    var materiasHorario : ArrayList<MateriasHorario> = remember { arrayListOf() }
    val isHorarioFound = remember { mutableStateOf(false)}
    val isHoraioLoaded = remember { mutableStateOf(false)}
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val coroutineScope = rememberCoroutineScope()


    if (userDataViewModel.isUserDataLoaded.value && !isHorarioFound.value){
        for (horario in userDataViewModel.horarios){
            if(horario.nombre == nombreHorario){
                isHorarioFound.value = true
            }
        }
    }

    if (isHorarioFound.value && !userDataViewModel.isMateriasUnicasFill.value){
        LaunchedEffect(key1 = Unit) {
            println("LaunchedEffect")
            if (!datosViewModel.materiasHorarioState.value){
                getMateriasHorario(datosViewModel = datosViewModel)
                delay(100)
            }
            getMateriasUnicas(nombreHorario = nombreHorario!!, userDataViewModel = userDataViewModel, userId = userId)
            getMateriasHorario(nombreHorario = nombreHorario, userDataViewModel = userDataViewModel, userId = userId)
        }
    }
    if (userDataViewModel.isMateriasHorarioFill.value && userDataViewModel.isMateriasUnicasFill.value){

        println("Antes de llenar " + materiasHorario.size)
        for (horario in userDataViewModel.horarios){
            if(horario.nombre == nombreHorario && horario.materiasUnicas.size > materiasUnicas.size){
                materiasHorario = horario.materiasHorarios
                materiasUnicas = horario.materiasUnicas
                println(materiasHorario.size)
            }
        }
        isHoraioLoaded.value = true
    }

    println("${isHorarioFound.value} --- ${isHoraioLoaded.value}")

    if (isHorarioFound.value && isHoraioLoaded.value){
        println("ya cargados " + materiasHorario.size)
        val pagerState = rememberPagerState(pageCount = 2)
        val elementList = listOf("Dia", "Semana")


        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxWidth()) {
                TabMenu(pagerState = pagerState, listElements = elementList, coroutineScope = coroutineScope)
                LazyColumn(
                    modifier =Modifier.padding(horizontal = 5.dp),
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars,
                        applyTop = false
                    )
                ){
                    item {  TabsContent(pagerState = pagerState, userDataViewModel = userDataViewModel, materiasHorario = materiasHorario, materiasUnicas = materiasUnicas) }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            RoundedButton(
                                text = "Agregar Materia",
                                width = 140.dp,
                                height =  40.dp,
                                fontSize = 14.sp,
                                onClick = { onNavToAddSubject(nombreHorario!!) }
                            )
                        }
                    }
                }
            }
        }
    }else {
        LoadingIndicator()
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
private fun TabsContent(
    pagerState: PagerState,
    userDataViewModel: UserDataViewModel,
    materiasHorario: ArrayList<MateriasHorario>,
    materiasUnicas: ArrayList<Materias>
) {

    when(pagerState.currentPage){

        0 -> {
            VerPorDiaTab(userDataViewModel = userDataViewModel, materiasHorario = materiasHorario, materiasUnicas = materiasUnicas)
        }

        1 -> {}

    }
}

@ExperimentalAnimationApi
@Composable
private fun CardMateriasPorDia(
    modifier: Modifier = Modifier,
    dia : String,
    materiasHorario : List<MateriasHorario>,
    materiasUnicas: List<Materias>
) {
    val expanded = remember{ mutableStateOf(false)}

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .clickable { expanded.value = !expanded.value },
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = primaryColorCustom),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 5.dp
    ) {


        Column(modifier = modifier
            .fillMaxWidth()
            .padding(3.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = dia,
                style = TextStyle(
                    color = primaryColorCustom,
                    fontFamily = sansPro,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            AnimatedVisibility(visible = expanded.value) {
                HorarioPorDia(materiasHorarios = materiasHorario, materiasUnicas = materiasUnicas)
            }
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .wrapContentSize(align = Alignment.Center),
                imageVector = if(!expanded.value) Icons.Rounded.ExpandMore
                else Icons.Rounded.ExpandLess,
                contentDescription = "",
                tint = primaryColorCustom
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun TestCardMateriasPorDia() {


    val materia = listOf(
        MateriasHorario("L", "Edif CCO2", "0900", "23123", "0959"),
        MateriasHorario("L", "Edif CCO1", "1200", "23123", "1259"),
        MateriasHorario("L", "Edif CCO3", "1300", "23123", "1359")
    )
    val materiasUnicas = listOf(
        Materias())

    CardMateriasPorDia(dia = "Lunes", materiasHorario = materia, materiasUnicas = materiasUnicas)
}

@ExperimentalAnimationApi
@Composable
private fun VerPorDiaTab(
    userDataViewModel: UserDataViewModel,
    materiasHorario: ArrayList<MateriasHorario>,
    materiasUnicas: ArrayList<Materias>
) {
    val dias = listOf("L", "A", "M", "J", "V", "S")
    var horarios: ArrayList<MateriasHorario>

    for (dia in dias){
        horarios = buscarHoriosPorDia(dia = dia, materiasHorario = materiasHorario)
        if (horarios.size != 0){
            when(dia){
                "L" -> {CardMateriasPorDia(dia = "Lunes", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
                "A" -> {CardMateriasPorDia(dia = "Martes", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
                "M" -> {CardMateriasPorDia(dia = "Miercoles", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
                "J" -> {CardMateriasPorDia(dia = "Jueves", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
                "V" -> {CardMateriasPorDia(dia = "Viernes", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
                "S" -> {CardMateriasPorDia(dia = "Sabado", materiasHorario = horarios, materiasUnicas = materiasUnicas)}
            }
        }
    }
}

@Composable
private fun VerPorSemanaTab() {

}

private fun buscarHoriosPorDia(
    dia : String,
    materiasHorario: ArrayList<MateriasHorario>
)
: ArrayList <MateriasHorario> {
    val horarios : ArrayList<MateriasHorario> = arrayListOf()
    for (horario in materiasHorario){
        if (horario.dia == dia){
            horarios.add(horario)
        }
    }
    return horarios
}

private fun ordenarHorarios(
    materias: List<MateriasHorario>
) : ArrayList<MateriasHorario>{
    val horariosOrdenados = materias as ArrayList
    var auxHora =  2400
    var auxHorario = MateriasHorario()
    var indexMenor = 0

    for (i in 0 until horariosOrdenados.size - 1){
        for (j in i until horariosOrdenados.size){
            if (auxHora > horariosOrdenados[j].entrada.toInt()){
                auxHora = horariosOrdenados[j].entrada.toInt()
                indexMenor = j
            }
        }
        auxHorario = horariosOrdenados[indexMenor]
        horariosOrdenados[indexMenor] = horariosOrdenados[i]
        horariosOrdenados[i] = auxHorario
        auxHora = 2400

    }

    return horariosOrdenados
}

@Composable
private fun HorarioPorDia(
    materiasHorarios: List<MateriasHorario>,
    materiasUnicas: List<Materias>
) {
    val horarios = ordenarHorarios(materias = materiasHorarios)
    var clase : Materias?

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TableCell(text = "Materia", weight = 0.7f, fontWeight = FontWeight.Bold, textSize = 16.sp)
            TableCell(text = "Horario", weight = 0.3f, fontWeight = FontWeight.Bold, textSize = 16.sp)
        }
        Divider(modifier = Modifier.padding(horizontal = 5.dp), color = primaryColorCustom, thickness = 2.dp)
        for (horario in horarios){
            clase = materiasUnicas.find { materia -> materia.nrc == horario.nrc }
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(text = clase?.nombre ?: "No encontrada", weight = 0.7f)
                TableCell(text = horario.entrada + " - " + horario.salida, weight = 0.3f)
            }
            Divider(modifier = Modifier.padding(horizontal = 5.dp), color = primaryColorCustom, thickness = 1.dp)
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor : Color = primaryColorCustom,
    textSize : TextUnit = 13.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    borderColor : Color = Color.Transparent
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, color = borderColor)
            .weight(weight)
            .padding(8.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = textColor,
            fontSize = textSize,
            fontFamily = sansPro,
            fontWeight = fontWeight
        )
    )
}