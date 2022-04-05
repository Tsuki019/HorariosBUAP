package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Brush
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
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.horariosbuap.R
import com.example.horariosbuap.model.Edificios
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.model.Profesores
import com.example.horariosbuap.model.Salones
import com.example.horariosbuap.ui.theme.customStuff.components.ButtonToggleGroup
import com.example.horariosbuap.ui.theme.customStuff.components.pageNavigator
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.*
import com.example.horariosbuap.ui.theme.light_blue1
import com.example.horariosbuap.ui.theme.light_blue2
import com.example.horariosbuap.ui.theme.secondaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@ExperimentalAnimationApi
@Composable
fun ResultadosBusqueda(
    datosViewModel: DatosViewModel,
    titulos : MutableState<String>,
    onNavToSubject : (String, String) -> Unit,
    onNavToClassRooms : (String) -> Unit
) {

    when(datosViewModel.tipoBusqueda.value){

        1 -> {
            titulos.value = "Buscar Profesor"
            VistaProfesores(datosViewModel = datosViewModel)
        }
        2 -> {
            titulos.value = "Buscar Salones"
            VistaEdificios(datosViewModel = datosViewModel, onNavToClassRooms = onNavToClassRooms)
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

    //val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    //val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val textBusq = remember {mutableStateOf("")}
    val busquedaOpc = rememberSaveable{ mutableStateOf("Nombre")}
    val pagina = remember {mutableStateOf(1)}
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)) {
            Text(
                modifier = Modifier.padding(end = 5.dp),
                text = "Buscar salon por:",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold),
                fontSize = 16.sp,
                fontFamily = sansPro
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
                         tint = MaterialTheme.colors.secondary
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
                cursorColor = light_blue2,
                focusedIndicatorColor = light_blue2,
                focusedLabelColor = light_blue2,
                unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                textColor = MaterialTheme.colors.primary
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
                        ListaDivider()
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
                        ListaDivider()
                    }else{
                        if (textBusq.value.length > 1){
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ListaDivider()
                                Text(
                                    text = "Sin coincidencias.",
                                    style = TextStyle(
                                        color = MaterialTheme.colors.primary,
                                        fontSize = 25.sp,
                                        fontFamily = sansPro
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
fun VistaEdificios(
    datosViewModel: DatosViewModel,
    onNavToClassRooms: (String) -> Unit)
{
    val focusManager = LocalFocusManager.current
    val textBusq = remember {mutableStateOf("")}
    val busquedaOpc = rememberSaveable{ mutableStateOf("Salon")}

    if (!datosViewModel.edificiosState.value)
        getEdificios(datosViewModel = datosViewModel)
    else
        getSalones(datosViewModel = datosViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 5.dp)) {
            Text(
                modifier = Modifier.padding(end = 5.dp),
                text = "Buscar salon por:",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
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
                datosViewModel.buscarSalones(key = textBusq)
            },
            label = { Text(text = "Buscar Salon")},
            trailingIcon = {
                IconButton(onClick = {
                    datosViewModel.busquedaState.value = false
                    when (busquedaOpc.value){
                        "Salon" -> {
                            datosViewModel.buscarSalones(key = textBusq)
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Rounded.Search,
                         contentDescription = "",
                         tint = MaterialTheme.colors.secondary
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
                cursorColor = light_blue2,
                focusedIndicatorColor = light_blue2,
                focusedLabelColor = light_blue2,
                unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                textColor = MaterialTheme.colors.primary
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

                item {
                    if (datosViewModel.edificiosState.value){
                        for (datos in datosViewModel.edificios){
                            CardEdicios(datos = datos, onNavToClassRoom = onNavToClassRooms)
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .padding(top = 30.dp, bottom = 20.dp),
                            color = MaterialTheme.colors.onSurface,
                            thickness = 1.dp
                        )
                    }else{
                        LoadingIndicator()
                    }
                }
            }else{
                item {
                    if (datosViewModel.busquedaState.value){

                        for (salon in datosViewModel.resultSalones){
                            CardSalones(salon = salon!!)
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .padding(top = 30.dp, bottom = 20.dp),
                            color = MaterialTheme.colors.onSurface,
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
                                    color = MaterialTheme.colors.onSurface,
                                    thickness = 1.dp
                                )
                                Text(
                                    text = "Sin coincidencias.",
                                    style = TextStyle(
                                        color = MaterialTheme.colors.secondaryVariant,
                                        fontSize = 25.sp,
                                        fontFamily = sansPro
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
private fun VistaMaterias(
    datosViewModel: DatosViewModel,
    onNavToSubject: (String, String) -> Unit
) {
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
                .background(color = MaterialTheme.colors.background)
        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)) {
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "Buscar Materia por:",
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold),
                    fontSize = 15.sp,
                    fontFamily = sansPro
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
                             tint = MaterialTheme.colors.secondary
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
                    cursorColor = light_blue2,
                    focusedIndicatorColor = light_blue2,
                    focusedLabelColor = light_blue2,
                    unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
                    unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                    textColor = MaterialTheme.colors.primary
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
                                color = MaterialTheme.colors.onSurface,
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
                                color = MaterialTheme.colors.onSurface,
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
                                        color = MaterialTheme.colors.onSurface,
                                        thickness = 1.dp
                                    )
                                    Text(
                                        text = "Sin coincidencias.",
                                        style = TextStyle(
                                            color = MaterialTheme.colors.primary,
                                            fontSize = 25.sp,
                                            fontFamily = sansPro
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

@ExperimentalAnimationApi
@Composable
fun FloatingInfoBottom() {
    val stateButtom = remember { mutableStateOf(false) }
    val datosTextStyle  = SpanStyle(
        color = Color.White,
        fontSize = 13.sp,
        fontFamily = sansPro
    )

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { stateButtom.value = !stateButtom.value }
            .background(color = MaterialTheme.colors.onSurface)
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
private fun CardSalones(
    modifier: Modifier = Modifier,
    salon : Salones
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = salon.data,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp), color = MaterialTheme.colors.onSurface)
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(text = "Edificio", weight = 0.5f, textColor = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold)
                TableCell(text = "Piso", weight = 0.5f, textColor = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                TableCell(text = salon.edificio, weight = 0.5f)
                TableCell(text = salon.piso, weight = 0.5f)
            }

        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor : Color = MaterialTheme.colors.secondary,
    textSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        Modifier
//          .border(1.dp, color = colorResource(id = R.color.azulOscuroInstitucional))
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

@Composable
private fun CardProfesor(
    datos : Profesores?
) {

    val datosTextStyle  = SpanStyle(
        color = MaterialTheme.colors.secondary,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        fontFamily = sansPro
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
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
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                fontFamily = sansPro
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = modifier,
                text = if (datos.puesto != "") datos.puesto else "Profesor",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = sansPro
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            SelectionContainer(modifier = modifier) {
                Column() {
                    if (datos.ext != ""){
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= MaterialTheme.colors.primary)){
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
                                withStyle(style = datosTextStyle.copy(color= MaterialTheme.colors.primary)){
                                    append("Cubiculo: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.cubiculo)
                                }
                            }
                        )
                    }
                    Text(
                        buildAnnotatedString {
                            withStyle(style = datosTextStyle.copy(color= MaterialTheme.colors.primary)){
                                append("Correo: ")
                            }
                            withStyle(style = datosTextStyle){
                                append(datos.correo)
                            }
                        }
                    )
                    if (datos.correo2 != ""){
                        Text(
                            buildAnnotatedString {
                                withStyle(style = datosTextStyle.copy(color= MaterialTheme.colors.primary)){
                                    append("Correo: ")
                                }
                                withStyle(style = datosTextStyle){
                                    append(datos.correo2)
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun CardEdicios(
    datos : Edificios?,
    onNavToClassRoom: (String) -> Unit
) {
    val modifier = Modifier.padding(start = 2.dp)
    val expanded = remember{ mutableStateOf(false)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberImagePainter(data = datos!!.imagen),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.CenterStart
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color.Transparent, MaterialTheme.colors.surface),
                                startX = 0f,
                                endX = Float.POSITIVE_INFINITY * 0.4f
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp, bottom = 8.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 3.dp),
                        text = "Edificio: ${datos.nombre}",
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold),
                        fontSize = 23.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)
                        )
                    )
                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 3.dp, vertical = 2.dp)
                            .fillMaxWidth(0.4f),
                        thickness = 1.dp,
                        color = MaterialTheme.colors.onSurface
                    )
                    ClickableText(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 4.dp),
                        text = AnnotatedString(
                            text = "Ver salones",
                            spanStyle = SpanStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.primary,
                                fontFamily = FontFamily(Font(R.font.source_sans_pro)
                                )
                            ),
                            paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
                        ),
                        onClick = {onNavToClassRoom(datos.nombre)}
                    )
                }
            }
        }

    }
}

@Composable
private fun CardMaterias(
    datos : Materias?,
    onNavToSubject: (String, String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onNavToSubject(datos!!.nrc, " ") },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
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
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                fontFamily = sansPro
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = modifier,
                text = "Porfesor(a): " + datos.profesor,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = sansPro
                )
            )
            Row(
                Modifier.fillMaxWidth().padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "NRC",
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = sansPro
                        )
                    )
                        Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.nrc,
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            fontFamily = sansPro
                        )
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "Sección",
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = sansPro
                        )
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.secc,
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            fontFamily = sansPro
                        )
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = modifier,
                        text = "Clave",
                        style = TextStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = sansPro
                        )
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = datos.clave,
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            fontFamily = sansPro
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ListaDivider () {
    Divider(    //Divider del fondo de la lista
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .padding(top = 30.dp, bottom = 20.dp),
        color = MaterialTheme.colors.onSurface,
        thickness = 1.dp
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PrevEdificioCard() {

    val edificio : Edificios = Edificios("CCO1", imagen = "https://firebasestorage.googleapis.com/v0/b/horariosbuap.appspot.com/o/Edificios%2Fcco1.jpg?alt=media&token=e415c1e9-1706-480b-8683-702188d21d90", descripcion = "En este edificio se encuentra el POSGRADO de la Facultad de Ciencias de la Computación, además el consultorio médico, cubículos de profesores y salones de clase, numerados del modo 101 al 105.")

    CardEdicios(datos = edificio, onNavToClassRoom = {})
}