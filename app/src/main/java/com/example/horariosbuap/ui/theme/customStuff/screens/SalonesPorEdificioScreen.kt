package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.BackArrowButtonn
import com.example.horariosbuap.model.Salones
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dark_blue2
import com.example.horariosbuap.ui.theme.dataBase.getMaterias
import com.example.horariosbuap.ui.theme.dataBase.getMateriasHorario
import com.example.horariosbuap.ui.theme.dataBase.getPeriodoActual
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private val numSalon = mutableStateOf("")
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SalonesPorEdificio(
    salones : ArrayList<Salones?>,
    edificio : String?,
    onBack : () -> Unit,
    datosViewModel: DatosViewModel
) {
    if(!salones.isEmpty()){
        val pisos = listOf("Sotano", "1er Piso", "2do Piso", "3er Piso", "4to Piso", "5to Piso", "6to Piso")
        var cont = 0
        val mostrador = remember { mutableStateOf(-1) }
        val coroutineScope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var materiasEdificio: List<MateriasHorario?> = listOf()
        var periodoActual = ""


        if(!datosViewModel.materiasState.value || !datosViewModel.materiasHorarioState.value || !datosViewModel.periodoState.value){
            LaunchedEffect(key1 = 1, block = {
                getMaterias(datosViewModel = datosViewModel)
                getMateriasHorario(datosViewModel = datosViewModel)
                getPeriodoActual(datosViewModel = datosViewModel)
            })

        }
        if(datosViewModel.materiasHorarioState.value){
            materiasEdificio = datosViewModel.materiasHorario.filter { materiasHorario -> materiasHorario!!.edificio.contains(edificio!!)  }
            periodoActual = datosViewModel.periodoActual.value
        }

        if(datosViewModel.materiasHorarioState.value){
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(materiasEdificio.isNotEmpty()){
                            BottomSheetContent(
                                materias = datosViewModel.materias,
                                materiasEdifio = materiasEdificio,
                                periodoActual = periodoActual
                            )
                            Spacer(Modifier.height(30.dp))
                        }else{
                            SinClasesAviso()
                        }
                    }
                },
                sheetState = bottomSheetState,
                sheetShape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
                sheetElevation = 8.dp
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BackArrowButtonn(onBack = { onBack() })
                            Text(
                                modifier = Modifier
                                    .padding(top = 3.dp),
                                text = "Salones en: $edificio",
                                style = TextStyle(
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Bold
                                ),
                                fontSize = 24.sp,
                                fontFamily = sansPro
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Pulse el número de salón para ver su horario en $periodoActual",
                                style = TextStyle(
                                    color = MaterialTheme.colors.primary,
                                    fontWeight = FontWeight.Normal
                                ),
                                fontSize = 14.sp,
                                fontFamily = sansPro,
                                textAlign = TextAlign.Center
                            )
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = rememberInsetsPaddingValues(
                                insets = LocalWindowInsets.current.systemBars,
                                applyTop = false
                            ),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(pisos) {
                                MostrarSalones(
                                    salones = salones,
                                    piso = it,
                                    edificio = edificio,
                                    mostrador = mostrador,
                                    indexPiso = cont,
                                    bottomSheetState = bottomSheetState,
                                    coroutineScope = coroutineScope
                                )
                                cont++
                            }
                        }
                    }
                }
            }
        }else{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        }

    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
private fun BottomSheetContent(
    materias: List<Materias?>,
    materiasEdifio: List<MateriasHorario?>,
    periodoActual: String
) {
    val materiasSalon = materiasEdifio.filter { it!!.salon == numSalon.value}

    if(materiasSalon.isNotEmpty()){
        HorarioSalon(
            materias = materias,
            materiasSalon = materiasSalon,
            periodoActual = periodoActual
        )
    }else{
        SinClasesAviso()
    }

}

private fun sortMateriasPorDia(materias: List<MateriasHorario?>): List<MateriasHorario?> {
    val dias: HashMap<String, Int> = hashMapOf(
        "L" to 0,
        "A" to 1,
        "M" to 2,
        "J" to 3,
        "V" to 4,
        "S" to 5
    )

    val comparator = Comparator { o1: MateriasHorario, o2: MateriasHorario ->
        return@Comparator dias[o1.dia]!! - dias[o2.dia]!!
    }
    val copy = arrayListOf<MateriasHorario?>().apply { addAll(materias) }
    copy.sortWith(comparator)
    return copy
}

private fun eliminarDuplicadosEntrada(materias: List<MateriasHorario?>): List<MateriasHorario?>{
    val verificador:ArrayList<MateriasHorario?> = arrayListOf()

    materias.forEach { materia ->
        val temp = verificador.find { it!!.entrada == materia!!.entrada && it.dia == materia.dia}
        if(temp == null){
            verificador.add(materia)
        }

    }
    return verificador
}

private fun seleccionarPorPeriodo(
    horarios: List<MateriasHorario?>,
    materias: List<Materias?>,
    periodo: String
): List<MateriasHorario?>{
    val listaResultado = ArrayList<MateriasHorario?>()

    horarios.forEach { horario ->
        val temp = materias.find { horario!!.nrc == it!!.nrc && it.periodo == periodo }

        if(temp != null){
            listaResultado.add(horario)
        }
    }
    return  listaResultado
}

@ExperimentalFoundationApi
@Composable
private fun HorarioSalon(
    materias: List<Materias?>,
    materiasSalon: List<MateriasHorario?>,
    periodoActual: String
) {

    var materiasOrder = sortMateriasPorDia(materias = materiasSalon)
    materiasOrder = seleccionarPorPeriodo(
        horarios = materiasOrder,
        materias = materias,
        periodo = periodoActual
    )

    materiasOrder = eliminarDuplicadosEntrada(materias = materiasOrder)
    val groupedItems = materiasOrder.groupBy { it!!.dia }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = dark_blue2),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Salón ${numSalon.value}",
            modifier = Modifier.padding(10.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 26.sp,
                fontFamily = sansPro,
                fontWeight = FontWeight.Black
            )
        )
    }
    if (groupedItems.isEmpty()){
        SinClasesAviso()
    }else{
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            groupedItems.forEach { (header, items) ->

                stickyHeader {

                    val diaHeader =
                        when(header){
                            "L" -> "Lunes"
                            "A" -> "Martes"
                            "M" -> "Miércoles"
                            "J" -> "Jueves"
                            "V" -> "Viernes"
                            else -> "Sábado"
                        }

                    Text(
                        text = diaHeader,
                        modifier = Modifier
                            .background(MaterialTheme.colors.onSurface)
                            .padding(5.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                }
                if (items.isEmpty()){
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TableCell(text = "Sin Clases", weight = 1f)
                        }
                    }
                }else{
                    items(items.sortedBy { it!!.entrada }) { item ->
                        val materia = materias.find { it!!.nrc == item!!.nrc }

                        if (materia != null){
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TableCell(text = "${item!!.entrada} - ${item.salida}", weight = 0.3f)
                                TableCell(text = materia.nombre, weight = 0.7f)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun SinClasesAviso() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sin clases.",
            style = TextStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontFamily = sansPro,
                fontWeight = FontWeight.Black
            )
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MostrarSalones(
    salones : ArrayList<Salones?>,
    piso : String,
    edificio : String?,
    mostrador : MutableState<Int>,
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    indexPiso  : Int
) {
    val salonesEdi = arrayListOf<Salones>()

    for(salon in salones){
        if (salon!!.edificio == edificio){
            salonesEdi.add(salon)
        }
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val salonesPiso = salonesEdi.filter { it.piso == piso }
        if (salonesPiso.isNotEmpty()){

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = piso,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Divider(
                modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 20.dp),
                color = MaterialTheme.colors.primaryVariant
            )
            IconButton(
                modifier = Modifier.padding(vertical = 1.dp),
                onClick = {  if (mostrador.value == indexPiso) mostrador.value = -1 else mostrador.value = indexPiso }
            ) {
                if (mostrador.value != indexPiso)
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "",
                        tint = MaterialTheme.colors.primaryVariant )
                else
                    Icon(imageVector = Icons.Rounded.ExpandLess, contentDescription = "",
                        tint = MaterialTheme.colors.primaryVariant)
            }

            AnimatedVisibility(visible = mostrador.value == indexPiso) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colors.onSurface.copy(
                                0.8f
                            )
                        )
                    ) {
                        TableCell(text = "Salon", weight = 0.3f, textColor = Color.White, textSize = 16.sp, fontWeight = FontWeight.Medium)
                        TableCell(text = "Tipo", weight = 0.7f, textColor = Color.White, textSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                    for (salon in salonesPiso){
                        Row() {
                            TableCell(text = salon.salonId, weight = 0.3f, clickable = true,
                                onClick = {
                                    coroutineScope.launch { bottomSheetState.show() }
                                    numSalon.value = salon.salonId
                                }
                            )
                            TableCell(text = salon.tipo, weight = 0.7f)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor : Color = MaterialTheme.colors.primary,
    textSize : TextUnit = 13.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    clickable: Boolean = false,
    onClick: ()->Unit = {}
) {
    if(!clickable){
        Text(
            text = text,
            Modifier
                .border(1.dp, color = MaterialTheme.colors.onSurface)
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
    }else{
        ClickableText(
            text = AnnotatedString(text = text),
            modifier = Modifier
                .border(1.dp, color = MaterialTheme.colors.onSurface)
                .weight(weight)
                .padding(8.dp),
            style = TextStyle(
                color = textColor,
                fontSize = textSize,
                fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                fontWeight = fontWeight,
                textAlign = TextAlign.Center
            ),
            onClick = { onClick() }
        )
    }

}