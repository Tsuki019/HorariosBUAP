package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.components.TabMenu
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.*
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.ui.theme.secundaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@ExperimentalAnimationApi

@ExperimentalPagerApi
@Composable
fun DetallesHorarioScreen(
    nombreHorario :String?,
    userDataViewModel: UserDataViewModel,
    onNavToAddSubject: (String) -> Unit,
    datosViewModel : DatosViewModel,
) {
    var materiasUnicas : ArrayList<Materias> =  remember { arrayListOf() }
    var materiasHorario : ArrayList<MateriasHorario> = remember { arrayListOf() }
    val isHorarioFound = remember { mutableStateOf(false)}
    val isHoraioLoaded = remember { mutableStateOf(false)}
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val coroutineScope = rememberCoroutineScope()
    val cantidadMaterias = remember { mutableStateOf(0)}
    val actualizarDatos = remember { mutableStateOf(false)}
    val materiaEliminar = remember { mutableStateOf( Materias())}
    val nombreHorarioState = remember { mutableStateOf("")}

    if (userDataViewModel.isUserDataLoaded.value && !isHorarioFound.value){
        for (horario in userDataViewModel.horarios){
            if(horario.nombre == nombreHorario){
                isHorarioFound.value = true
            }
        }
    }

    if (isHorarioFound.value && !userDataViewModel.isMateriasUnicasFill.value){
        LaunchedEffect(key1 = Unit) {
            if (!datosViewModel.materiasHorarioState.value){
                getMateriasHorario(datosViewModel = datosViewModel)
                delay(100)
            }
            getMateriasUnicas(nombreHorario = nombreHorario!!, userDataViewModel = userDataViewModel, userId = userId)
            getMateriasHorario(nombreHorario = nombreHorario, userDataViewModel = userDataViewModel, userId = userId)
        }
    }
    if (userDataViewModel.isMateriasHorarioFill.value && userDataViewModel.isMateriasUnicasFill.value){

        for (horario in userDataViewModel.horarios){
            if(horario.nombre == nombreHorario && horario.materiasUnicas.size > materiasUnicas.size){
                materiasHorario = horario.materiasHorarios
                materiasUnicas = horario.materiasUnicas
                nombreHorarioState.value = nombreHorario
            }
        }
        isHoraioLoaded.value = true
    }

    if (nombreHorarioState.value != nombreHorario){
        val horarioUsuario = userDataViewModel.horarios.find { horarioUsuario -> horarioUsuario.nombre == nombreHorario}
        if (horarioUsuario == null || horarioUsuario.materiasUnicas.isEmpty()){
            getMateriasUnicas(nombreHorario = nombreHorario!!, userDataViewModel = userDataViewModel, userId = userId)
            getMateriasHorario(nombreHorario = nombreHorario, userDataViewModel = userDataViewModel, userId = userId)
        }
        actualizarDatos.value = true
    }

    if (actualizarDatos.value){
        isHoraioLoaded.value = !isHoraioLoaded.value
        for (horario in userDataViewModel.horarios){
            if(horario.nombre == nombreHorario){
                materiasHorario = horario.materiasHorarios
                materiasUnicas = horario.materiasUnicas
                println("ACTUALIZA MATERIAS "+ horario.materiasHorarios.size)
            }
        }
        isHoraioLoaded.value = !isHoraioLoaded.value
        nombreHorarioState.value = nombreHorario!!
        actualizarDatos.value = !actualizarDatos.value
    }

    if (isHorarioFound.value && isHoraioLoaded.value){
        cantidadMaterias.value = materiasUnicas.size
        val pagerState = rememberPagerState(pageCount = 2)
        val elementList = listOf("Horario", "Materias")


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
                    item {
                        TabsContent(pagerState = pagerState, userDataViewModel = userDataViewModel, materiasHorario = materiasHorario, materiasUnicas = materiasUnicas)
                        Divider(modifier = Modifier.padding(vertical = 15.dp), color = Color.Transparent)
                    }
                }
            }
            if (cantidadMaterias.value  == 0){
                Box(modifier = Modifier.align(alignment = Alignment.Center)){
                    Text(
                        text = "Aún no haz agregado materias a este horario. Agregalas en el menú que está abajo a la izquirda.",
                        color = primaryColorCustom.copy(alpha = 0.5f),
                        fontFamily = sansPro,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(8.dp)
            ){
                FloatingMenu(
                    nombreHorario = nombreHorario!!,
                    onAgregarMateria = onNavToAddSubject,
                    cantidadMaterias = cantidadMaterias,
                    materiasUnicas = materiasUnicas,
                    materiaEliminar = materiaEliminar
                )
            }
            if (materiaEliminar.value != Materias()){
                AlertaBorrarMateria(
                    nombreHorario = nombreHorario!!,
                    materiaEliminar = materiaEliminar,
                    userDataViewModel = userDataViewModel,
                    actualizarDatos = actualizarDatos
                )
            }
        }
    }else {
        LoadingIndicator()
    }
}

@ExperimentalMaterialApi
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
            VerPorDiaTab(materiasHorario = materiasHorario, materiasUnicas = materiasUnicas)
        }

        1 -> {
            VerMateriasInscritas(materiasHorario = materiasHorario, materiasUnicas = materiasUnicas)
        }

    }
}

@ExperimentalMaterialApi
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
            .padding(horizontal = 10.dp, vertical = 15.dp),
        backgroundColor = Color.White,
        border = BorderStroke(width = 1.dp, color = primaryColorCustom),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 5.dp,
        onClick = { expanded.value = !expanded.value }
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
@Composable
private fun CardMateriasInscritas(
    materia: Materias,
    materiasHorario: List<MateriasHorario>
) {
    val infoVisibility = remember { mutableStateOf(false)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        backgroundColor = Color.White,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azulOscuroInstitucional)),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        val modifier = Modifier.padding(start = 5.dp)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = modifier
                    .padding(top = 3.dp)
                    .fillMaxWidth(),
                text = materia.nombre,
                color = primaryColorCustom,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, fontFamily = sansPro,
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 2.dp,
                color = primaryColorCustom
            )
            Text(
                modifier = modifier.padding(vertical = 5.dp),
                text = "Porfesor(a): " + materia.profesor,
                color = primaryColorCustom,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = sansPro
            )
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TableCell(text = "Materia", weight = 0.2f, fontWeight = FontWeight.Bold, textSize = 16.sp)
                    TableCell(text = "Materia", weight = 0.2f, fontWeight = FontWeight.Bold, textSize = 16.sp)
                    TableCell(text = "Materia", weight = 0.2f, fontWeight = FontWeight.Bold, textSize = 16.sp)
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    TableCell(text = materia.nrc, weight = 0.2f, textColor = secundaryColorCustom)
                    TableCell(text = materia.secc, weight = 0.2f, textColor = secundaryColorCustom)
                    TableCell(text = materia.clave, weight = 0.2f, textColor = secundaryColorCustom)
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = primaryColorCustom
            )
            AnimatedVisibility(visible = infoVisibility.value) {
                var dia = ""
                val dias = listOf("L", "A", "M", "J", "V", "S")

                Column(modifier = Modifier.fillMaxWidth()) {
                    dias.forEach{currentDia ->
                        val horario = materiasHorario.find { materiasHorario -> materiasHorario.dia == currentDia }
                        if (horario != null){
                            when (currentDia){
                                "L" -> {dia = "Lunes"}
                                "A" -> {dia = "Martes"}
                                "M" -> {dia = "Miercoles"}
                                "J" -> {dia = "Jueves"}
                                "V" -> {dia = "Viernes"}
                                "S" -> {dia = "Sabado"}
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TableCell(text = dia, weight = 1f, fontWeight = FontWeight.Bold, textSize = 16.sp)
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TableCell(text = "Entrada", weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp)
                                TableCell(text = "Salida", weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp)
                                TableCell(text = "Salón", weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp)
                                TableCell(text = "Edificio", weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp)
                            }
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TableCell(text = horario.entrada, weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp, textColor = secundaryColorCustom)
                                TableCell(text = horario.salida, weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp, textColor = secundaryColorCustom)
                                TableCell(text = horario.salon, weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp, textColor = secundaryColorCustom)
                                TableCell(text = horario.edificio, weight = 0.25f, fontWeight = FontWeight.Bold, textSize = 14.sp, textColor = secundaryColorCustom)
                            }
                            Divider(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                color = primaryColorCustom,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center)
            {
                IconButton(onClick = { infoVisibility.value = !infoVisibility.value }) {
                    Icon(imageVector = if (infoVisibility.value) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                        contentDescription = "",
                        tint = primaryColorCustom
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun VerPorDiaTab(
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

@ExperimentalAnimationApi
@Composable
private fun VerMateriasInscritas(
    materiasHorario: ArrayList<MateriasHorario>,
    materiasUnicas: ArrayList<Materias>
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        materiasUnicas.forEach { it ->
            val horarios = materiasHorario.filter { materiasHorario -> materiasHorario.nrc == it.nrc }
            CardMateriasInscritas(materia = it, materiasHorario = horarios)
        }
    }
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
            TableCell(text = "Materia", weight = 0.4f, fontWeight = FontWeight.Bold, textSize = 16.sp)
            TableCell(text = "Horario", weight = 0.3f, fontWeight = FontWeight.Bold, textSize = 16.sp)
            TableCell(text = "Salón", weight = 0.3f, fontWeight = FontWeight.Bold, textSize = 16.sp)
        }
        Divider(modifier = Modifier.padding(horizontal = 5.dp), color = primaryColorCustom, thickness = 2.dp)
        for (horario in horarios){
            clase = materiasUnicas.find { materia -> materia.nrc == horario.nrc }
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(text = clase?.nombre ?: "No encontrada", weight = 0.4f)
                TableCell(text = horario.entrada + " - " + horario.salida, weight = 0.3f)
                TableCell(text = horario.salon +" - "+horario.edificio, weight = 0.3f)
            }
            Divider(modifier = Modifier.padding(horizontal = 5.dp), color = primaryColorCustom, thickness = 1.dp)
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun FloatingMenu(
    nombreHorario: String,
    onAgregarMateria : (String) -> Unit,
    cantidadMaterias: MutableState<Int>,
    materiasUnicas: ArrayList<Materias>,
    materiaEliminar: MutableState<Materias>
) {
    val menuVisibility = remember { mutableStateOf(false) }
    val borrarMaterias = remember { mutableStateOf(false)}

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = primaryColorCustom)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        if (borrarMaterias.value){
            IconButton(onClick = {
                borrarMaterias.value = !borrarMaterias.value
                menuVisibility.value = true
            }) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "", tint = Color.White)
            }
        }else{
            IconButton(onClick = { menuVisibility.value = !menuVisibility.value}) {
                if (menuVisibility.value) Icon(imageVector = Icons.Rounded.Close, contentDescription = "", tint = Color.White)
                else Icon(imageVector = Icons.Rounded.MenuOpen, contentDescription = "", tint = Color.White)
            }
        }

        AnimatedVisibility(visible = menuVisibility.value) {
            Column() {
                ClickableText(
                    text= AnnotatedString(
                        text = "Agregar Materia",
                        spanStyle = SpanStyle(color = Color.White, fontFamily = sansPro, fontSize = 12.sp)
                    ),
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        onAgregarMateria(nombreHorario)
                    }
                )
                Divider(modifier = Modifier
                    .padding(horizontal = 2.dp, vertical = 1.dp)
                    .width(100.dp), color = Color.White, thickness = 1.dp)
                if (cantidadMaterias.value > 0){
                    ClickableText(
                        text= AnnotatedString(
                            text = "Eliminar Materia",
                            spanStyle = SpanStyle(color = Color.White, fontFamily = sansPro, fontSize = 12.sp)
                        ),
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            borrarMaterias.value = !borrarMaterias.value
                            menuVisibility.value = !menuVisibility.value
                        }
                    )
                }
            }
        }
        AnimatedVisibility(visible = borrarMaterias.value) {
            LazyColumn(){
                items(materiasUnicas){materia ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = "", tint = Color.White, )
                        ClickableText(
                            text= AnnotatedString(
                                text = materia.nombre + " > NRC: "+ materia.nrc,
                                spanStyle = SpanStyle(color = Color.White, fontFamily = sansPro, fontSize = 12.sp)
                            ),
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                materiaEliminar.value = materia
                            }
                        )
                    }
                    Divider(modifier = Modifier.padding(horizontal = 2.dp), color = Color.White, thickness = 1.dp)
                }
                item {
                    ClickableText(
                        text= AnnotatedString(
                            text = "Cancelar",
                            spanStyle = SpanStyle(color = Color.White, fontFamily = sansPro, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        ),
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            borrarMaterias.value = !borrarMaterias.value
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun AlertaBorrarMateria(
    nombreHorario: String,
    materiaEliminar : MutableState<Materias>,
    userDataViewModel: UserDataViewModel,
    actualizarDatos : MutableState<Boolean>
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(8)),
        onDismissRequest = { materiaEliminar.value = Materias() },
        title = { Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Eliminar Materia",
            fontFamily = sansPro,
            textAlign = TextAlign.Center,
            color = primaryColorCustom,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ) },
        text = {Text(
            modifier = Modifier.fillMaxWidth(),
            text = "¿Borrar la materia ${materiaEliminar.value.nombre} de su horario?",
            fontFamily = sansPro,
            textAlign = TextAlign.Center,
            color = primaryColorCustom,
            fontSize = 15.sp
        ) },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ClickableText(
                    text= AnnotatedString(
                        text = "Borrar",
                        spanStyle = SpanStyle(color = Color.Red, fontFamily = sansPro, fontSize = 18.sp)
                    ),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    onClick = {
                        eliminarMateria(nrc = materiaEliminar.value.nrc, nombreHorario = nombreHorario, userDataViewModel = userDataViewModel, actualizarDatos = actualizarDatos)
                        materiaEliminar.value = Materias()
                    }
                )
                ClickableText(
                    text= AnnotatedString(
                        text = "Cancelar",
                        spanStyle = SpanStyle(color = primaryColorCustom, fontFamily = sansPro, fontSize = 18.sp)
                    ),
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    onClick = {
                        materiaEliminar.value = Materias()
                    }
                )
            }
        }

    )
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