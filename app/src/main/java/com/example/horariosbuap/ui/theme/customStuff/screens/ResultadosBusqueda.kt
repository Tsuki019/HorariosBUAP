package com.example.horariosbuap.ui.theme.customStuff.screens

import android.text.Layout
import android.widget.GridLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.ButtonToggleGroup
import com.example.horariosbuap.ui.theme.customStuff.components.pageNavigator
import com.example.horariosbuap.ui.theme.dataBase.*
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@ExperimentalAnimationApi
@Composable
fun ResultadosBusqueda(
    datosViewModel: DatosViewModel,
    titulos : MutableState<String>,
    onNavToSubject : (String) -> Unit
) {

    when(datosViewModel.tipoBusqueda.value){

        1 -> {
            titulos.value = "Buscar Profesor"
            VistaProfesores(datosViewModel = datosViewModel)
        }
        2 -> {
            titulos.value = "Buscar Salones"
            VistaEdificios(datosViewModel = datosViewModel)
        }
        3 -> {
            titulos.value = "Buscar Materias"
            VistaMaterias(datosViewModel = datosViewModel, onNavToSubject = onNavToSubject)
        }

    }

}

@Composable
private fun VistaProfesores(
    datosViewModel: DatosViewModel
) {

    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val textBusq = remember {mutableStateOf("")}
    val busquedaOpc = rememberSaveable{ mutableStateOf("Nombre")}
    val pagina = remember {mutableStateOf(1)}
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()



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
                text = "Buscar salon por:",
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro))
            )
            ButtonToggleGroup(
                options = listOf("Nombre", "Puesto"),
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
                        datosViewModel.buscarNombreProfesor(key = textBusq)
                    }
                    "Puesto"-> {
                        datosViewModel.buscarPuestoProfesor(key = textBusq)
                    }
                }
            },
            label = { Text(text = "Buscar Profesor")},
            trailingIcon = {
                IconButton(onClick = {
                    datosViewModel.busquedaState.value = false
                    when (busquedaOpc.value){
                        "Nombre" -> {
                            datosViewModel.buscarNombreProfesor(key = textBusq)
                        }
                        "Puesto"-> {
                            datosViewModel.buscarPuestoProfesor(key = textBusq)
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
            state= listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false
            )
        ){
            if (textBusq.value == ""){

                getProfesores(datosViewModel = datosViewModel)

                if (datosViewModel.profesoresState.value){
                    val ultimo : Int = datosViewModel.profesores.size / 50 + 1
                    item {
                        pageNavigator(pagina = pagina, datosViewModel = datosViewModel, ultimo = ultimo, listState = listState, coroutineScope = coroutineScope)
                    }
                    item{
                        var cont = 50*(pagina.value - 1)

                        while (cont <= pagina.value*50 && cont < datosViewModel.profesores.size){
                            CardProfesor(datos = datosViewModel.profesores[cont])
                            cont++
                        }

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
                        pageNavigator(pagina = pagina, datosViewModel = datosViewModel, ultimo = ultimo, listState =  listState, coroutineScope = coroutineScope)
                    }
                }else{
                    item{ LoadingIndicator() }
                }
            }else{
                item {
                    if (datosViewModel.busquedaState.value){
                        for (item in datosViewModel.resultProfesores){
                            CardProfesor(datos = item)
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
                        if (datosViewModel.resultProfesores.isEmpty()){
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
}

@ExperimentalAnimationApi
@Composable
fun VistaEdificios(datosViewModel: DatosViewModel) {
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val textBusq = remember {mutableStateOf("")}
    val busquedaOpc = rememberSaveable{ mutableStateOf("Salon")}

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
                text = "Buscar salon por:",
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro))
            )
            ButtonToggleGroup(
                options = listOf("Salon"),
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
                    "Salon" -> {
                        datosViewModel.buscarPorEdificio(key = textBusq)
                    }
                }


            },
            label = { Text(text = "Buscar Salon")},
            trailingIcon = {
                IconButton(onClick = {
                    datosViewModel.busquedaState.value = false
                    when (busquedaOpc.value){
                        "Salon" -> {
                            datosViewModel.buscarPorEdificio(key = textBusq)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false
            )
        ){
            if (textBusq.value == ""){

                getEdificios(datosViewModel = datosViewModel)
                item {
                    if (datosViewModel.edificiosState.value){
                        for (datos in datosViewModel.edificios){
                            CardEdicios(datos = datos)
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
                        LoadingIndicator()
                    }
                }
            }else{
                item {
                    if (datosViewModel.busquedaState.value){
                        for (item in datosViewModel.resultSalones){         //Cambiar a resultado por salones
                            CardEdicios(datos = item)                       //Hacer y cambiar cardSalones
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
                        if (datosViewModel.resultSalones.isEmpty()){
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
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun VistaMaterias(
    datosViewModel: DatosViewModel,
    onNavToSubject: (String) -> Unit
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

                                CardMaterias(datos = datosViewModel.materias[cont], onNavToSubject = onNavToSubject)

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
                                CardMaterias(datos = item, onNavToSubject = onNavToSubject)
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
                            if (datosViewModel.resultMaterias.isEmpty()){
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
private fun LoadingIndicator() {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
                .padding(15.dp),
            color = colorResource(id = R.color.azulClaroInstitucional)
        )
    }

}

@ExperimentalAnimationApi
@Composable
private fun FloatingInfoBottom() {
    val stateButtom = remember { mutableStateOf(false) }
    val datosTextStyle  = SpanStyle(
        color = Color.White,
        fontSize = 13.sp,
        fontFamily = FontFamily(Font(R.font.source_sans_pro))
    )

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { stateButtom.value = !stateButtom.value }
            .background(color = colorResource(id = R.color.azulOscuroInstitucional))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            modifier = Modifier.size(25.dp),
            imageVector =
             if (!stateButtom.value){
                Icons.Outlined.Info
             }else{
                 Icons.Rounded.ChevronRight
             },
             contentDescription = "", tint = Color.White)

        AnimatedVisibility(visible = stateButtom.value) {
            Text(
                buildAnnotatedString {
                    withStyle(style = datosTextStyle){
                        append("Las materias mostradas pertenecen al periodo escolar: ")
                    }
                    withStyle(style = datosTextStyle.copy(fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)){
                        append(stringResource(id = R.string.CicloEscolar))
                    }
                }
            )
        }
    }
}

@Composable
private fun CardProfesor(
    datos : Profesores?
) {

    val datosTextStyle  = SpanStyle(
        color = colorResource(id = R.color.azulClaroInstitucional),
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(R.font.source_sans_pro))
    )

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
                modifier = modifier.padding(top = 3.dp),
                text = datos!!.nombre,
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.source_sans_pro)
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            if (datos.puesto != ""){
                Text(
                    modifier = modifier,
                    text = datos.puesto,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro))
                    )
                )
            }else{
                Text(
                    modifier = modifier,
                    text = "Profesor",
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro))
                    )
                )
            }

            SelectionContainer(modifier = modifier) {
                Column() {
                    if (datos.ext != ""){
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= colorResource(id = R.color.azulOscuroInstitucional))){
                                    append("Ext: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.ext)
                                }
                            }
                        )
                    }
                    if (datos.cubiculo != ""){
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= colorResource(id = R.color.azulOscuroInstitucional))){
                                    append("Cubiculo: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.cubiculo)
                                }
                            }
                        )
                    }
                }
            }
            if (datos.correo2 != ""){
                SelectionContainer (
                    modifier = modifier
                ) {
                    Column() {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= colorResource(id = R.color.azulOscuroInstitucional))){
                                    append("Correo: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.correo)
                                }
                            }
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= colorResource(id = R.color.azulOscuroInstitucional))){
                                    append("Correo: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.correo2)
                                }
                            }
                        )
                    }
                }
            }else{
                SelectionContainer(
                    modifier = modifier.padding(bottom = 5.dp),
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = datosTextStyle.copy(color= colorResource(id = R.color.azulOscuroInstitucional))){
                                append("Correo: ")
                            }
                            withStyle(style = datosTextStyle){
                                append(datos.correo)
                            }
                        }
                    )
                }

            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun CardEdicios(
    datos : Edificios?
) {
    val modifier = Modifier.padding(start = 2.dp)
    val expanded = remember{ mutableStateOf(false)}

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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
        ) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.padding(top = 10.dp),
                    contentAlignment = Alignment.Center){
                    Image(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(corner = CornerSize(15)))
                            .width(100.dp)
                            .height(100.dp),
                        painter = rememberImagePainter(data = datos!!.imagen),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier.padding(top = 3.dp),
                        text = "Edificio: ${datos!!.nombre}",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)
                        )
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 3.dp, vertical = 2.dp),
                        thickness = 1.dp,
                        color = colorResource(id = R.color.azulOscuroInstitucional))
                    Text(
                        modifier = Modifier.padding(end = 2.dp),
                        text = datos.descripcion,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        ),
                        textAlign = TextAlign.Justify
                    )
                }
            }
            AnimatedVisibility(visible = expanded.value) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    text = "Expandido", textAlign = TextAlign.Center)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                if (expanded.value){
                    Icon(imageVector = Icons.Rounded.ExpandLess, contentDescription = "")
                }else{
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "")
                }
            }
        }

    }
}

@Composable
private fun CardMaterias(
    datos : Materias?,
    onNavToSubject: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onNavToSubject(datos!!.nrc) },
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
        }
    }
}