package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.ButtonToggleGroup
import com.example.horariosbuap.ui.theme.customStuff.components.pageNavigator
import com.example.horariosbuap.ui.theme.dataBase.DatosViewModel
import com.example.horariosbuap.ui.theme.dataBase.Materias
import com.example.horariosbuap.ui.theme.dataBase.getMaterias
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@ExperimentalAnimationApi
@Composable
fun AgregarMateriasScreen(
    datosViewModel : DatosViewModel,
    onNavToInfo : (String) -> Unit
) {

    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val textBusq = remember {mutableStateOf("")}
    val busquedaOpc = rememberSaveable{ mutableStateOf("Nombre")}
    val pagina = remember{ mutableStateOf(1)}
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.blanco_fondo))
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)) {
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "Buscar Materia por:",
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontWeight = FontWeight.Bold),
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro))
                )
                ButtonToggleGroup(
                    options = listOf("Nombre", "Profesor", "NRC"),
                    selectedOption = busquedaOpc.value,
                    onOptionSelect = { busquedaOpc.value  = it}
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                value = textBusq.value.take(40),
                onValueChange = {
                    textBusq.value = it
                    datosViewModel.busquedaState.value = false
                    when (busquedaOpc.value){
                        "Nombre" -> {
                            datosViewModel.buscarMateriaPorNombre(key = textBusq)
                        }
                        "Profesor"-> {
                            datosViewModel.buscarMateriaPorProfesor(key = textBusq)
                        }
                        "NRC" -> {
                            datosViewModel.buscarMateriaPorNRC(key = textBusq)
                        }
                    }


                },
                label = { Text(text = "Buscar Materia")},
                trailingIcon = {
                    IconButton(onClick = {
                        datosViewModel.busquedaState.value = false
                        when (busquedaOpc.value){
                            "Nombre" -> {
                                datosViewModel.buscarMateriaPorNombre(key = textBusq)
                            }
                            "Profesor"-> {
                                datosViewModel.buscarMateriaPorProfesor(key = textBusq)
                            }
                            "NRC" -> {
                                datosViewModel.buscarMateriaPorNRC(key = textBusq)
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            tint = colorResource(
                                id = R.color.azulOscuroInstitucional)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = azulClaro,
                    focusedIndicatorColor = azulClaro,
                    focusedLabelColor = azulClaro,
                    unfocusedIndicatorColor = azulOscuro,
                    unfocusedLabelColor = azulOscuro,
                    textColor = azulOscuro
                )
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = false
                )
            ){

                if (textBusq.value == ""){

                    getMaterias(datosViewModel = datosViewModel)

                    if (datosViewModel.materiasState.value) {
                        val ultimo: Int = datosViewModel.materias.size / 50 + 1

                        item {  }

                        item {
                            pageNavigator(
                                pagina = pagina,
                                datosViewModel = datosViewModel,
                                ultimo = ultimo,
                                listState = listState,
                                coroutineScope = coroutineScope
                            )
                        }

                        item (){
                            var cont = 50 * (pagina.value - 1)

                            while (cont <= pagina.value * 50 && cont < datosViewModel.materias.size) {

                                CardMaterias(datos = datosViewModel.materias[cont], onNavToInfo = onNavToInfo)

                                cont++
                            }
                        }
                        item {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .padding(top = 30.dp, bottom = 20.dp),
                                color = colorResource(id = R.color.azulOscuroInstitucional),
                                thickness = 1.dp
                            )
                        }
                        item {
                            pageNavigator(pagina = pagina, datosViewModel = datosViewModel, ultimo = ultimo, listState = listState, coroutineScope = coroutineScope)
                        }
                    }else{
                        item { LoadingIndicator() }
                    }
                }else{
                    item {
                        if (datosViewModel.busquedaState.value){
                            for (item in datosViewModel.resultMaterias){
                                CardMaterias(datos = item, onNavToInfo = onNavToInfo)
                            }
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .padding(top = 30.dp, bottom = 20.dp),
                                color = colorResource(id = R.color.azulOscuroInstitucional),
                                thickness = 1.dp
                            )
                        }else{
                            if (textBusq.value.length > 1){
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 20.dp)
                                            .padding(top = 30.dp),
                                        color = colorResource(id = R.color.azulOscuroInstitucional),
                                        thickness = 1.dp
                                    )
                                    Text(
                                        text = "Sin coincidencias.",
                                        style = TextStyle(
                                            color = colorResource(id = R.color.azulOscuroInstitucional),
                                            fontSize = 25.sp,
                                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                                        )
                                    )
                                }
                            }else{
                                LoadingIndicator()
                            }
                        }
                    }
                }
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ){
            FloatingInfoBottom()
        }
    }

}

@Composable
private fun CardMaterias(
    datos : Materias?,
    onNavToInfo: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
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
                modifier = modifier.padding(top = 3.dp), text = datos!!.nombre, style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold
                ), fontSize = 20.sp, fontFamily = FontFamily(
                    Font(R.font.source_sans_pro)
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            Text(
                modifier = modifier,
                text = "Porfesor(a): " + datos.profesor,
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro))
                )
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(end = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "NRC",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.nrc,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                }
                Column(modifier = Modifier.padding(end = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "Sección",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.secc,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                }
                Column(modifier = Modifier.padding(end = 2.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "Clave",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.clave,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )  {
                ClickableText(
                    modifier = modifier
                        .padding(bottom = 5.dp),
                    text = AnnotatedString(
                        text = "Ver Horario",
                        spanStyle = SpanStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    ),
                    onClick = {onNavToInfo(datos.nrc)}
                )
                Divider(modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .rotate(90f)
                    .width(20.dp), color = colorResource(id = R.color.azulOscuroInstitucional))
                ClickableText(
                    modifier = modifier
                        .padding(bottom = 5.dp),
                    text = AnnotatedString(
                        text = "Agregar",
                        spanStyle = SpanStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    ),
                    onClick = {}
                )
            }
        }
    }
}