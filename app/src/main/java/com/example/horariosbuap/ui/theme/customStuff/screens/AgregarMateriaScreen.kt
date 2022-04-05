package com.example.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
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
import com.example.horariosbuap.MainActivity
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.ButtonToggleGroup
import com.example.horariosbuap.ui.theme.customStuff.components.pageNavigator
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.model.Materias
import com.example.horariosbuap.ui.theme.dataBase.getMaterias
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.auth.FirebaseAuth
import com.example.horariosbuap.ui.theme.customStuff.components.AlertaConformacion
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.light_blue2

private val materiaElegida = mutableStateOf(Materias())

@ExperimentalAnimationApi
@Composable
fun AgregarMateriasScreen(
    nombreHorario : String,
    datosViewModel : DatosViewModel,
    onNavToInfo : (String, String) -> Unit,
    userDataViewModel: UserDataViewModel,
    activity: Activity
) {

    val userId = FirebaseAuth.getInstance().currentUser!!.uid
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

                                CardMaterias(datos = datosViewModel.materias[cont], onNavToInfo = onNavToInfo, nombreHorario = nombreHorario)

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
                                CardMaterias(datos = item, onNavToInfo = onNavToInfo, nombreHorario = nombreHorario)
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
private fun CardMaterias(
    datos : Materias?,
    onNavToInfo: (String, String) -> Unit,
    nombreHorario: String
) {

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
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = modifier,
                text = "Porfesor(a): " + datos.profesor,
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
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
                            fontSize = 15.sp,
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
                            fontSize = 15.sp,
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
                            fontSize = 15.sp,
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
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = MaterialTheme.colors.onSurface
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
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = sansPro
                        )
                    ),
                    onClick = {onNavToInfo(datos.nrc, nombreHorario)}
                )
                Divider(modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .rotate(90f)
                    .width(20.dp), color = MaterialTheme.colors.onSurface)
                ClickableText(
                    modifier = modifier
                        .padding(bottom = 5.dp),
                    text = AnnotatedString(
                        text = "Agregar",
                        spanStyle = SpanStyle(
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    ),
                    onClick = { materiaElegida.value = datos }
                )
            }
        }
    }
}