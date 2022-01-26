package com.example.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.components.OutlinedMediaButton
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import com.example.horariosbuap.model.MateriasHorario
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun HorarioScreen(
    isAnimationsOver : MutableState<Boolean>,
    navController: NavController,
    userDataViewModel: UserDataViewModel,
    datosViewModel: DatosViewModel,
    onAddSubject : () -> Unit,
    onNavToHorario : (String) -> Unit
) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val modifier = Modifier
    val coroutinScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blanco_fondo)))
    {
        if(currentUser != null && currentUser.isEmailVerified){

            if (userDataViewModel.isUserDataLoaded.value){
                if (!userDataViewModel.isMateriasUnicasFill.value){
                    VerHorariosScreen(
                        modifier = modifier,
                        datosViewModel = datosViewModel,
                        onAddSubject = onAddSubject,
                        userDataViewModel = userDataViewModel,
                        userId = auth.currentUser!!.uid,
                        onNavToHorario = onNavToHorario
                    )
                }
            }else{
                if (isAnimationsOver.value){
                    coroutinScope.launch {
                        getUserData(userDataViewModel = userDataViewModel, currentUser.uid)
                    }
                }
                LoadingIndicator()
            }

        }else{
            NoLoginScreen(
                modifier = modifier,
                onLogin = {navController.navigate(route = MainDestinations.LOGIN_ROUTE) },
                onRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)})
        }
        
    }
}

@ExperimentalAnimationApi
@Composable
fun VerHorariosScreen(
    modifier: Modifier,
    datosViewModel: DatosViewModel,
    onAddSubject: () -> Unit,
    userDataViewModel: UserDataViewModel,
    userId : String,
    onNavToHorario : (String) -> Unit
) {
    val isDeleting = remember { mutableStateOf(false)}
    val isAnimationVisible = remember { mutableStateOf(false)}
    val colorEnabeButton = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulOscuroInstitucional))
    val colorDisableButton = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulOscuroInstitucional).copy(0.4f))
    val isAlertVisible = remember { mutableStateOf(false)}
    val nombreHorarioActual = remember { mutableStateOf("")}
    val context = LocalContext.current

    if (userDataViewModel.userData.value.numHorarios == 0) isDeleting.value = false

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(visible = isAnimationVisible.value) {
                AgregarHorario(visibility = isAnimationVisible, userId = userId, userDataViewModel = userDataViewModel)
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 20.dp)
                    .wrapContentSize(align = Alignment.CenterStart),
                text = if (userDataViewModel.userData.value.numHorarios == 0) "Agrega un nuevo horario" else "Elige un horario",
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
            LazyColumn(){
                if (userDataViewModel.userData.value.numHorarios > 0 && userDataViewModel.isUserDataLoaded.value){
                    items(userDataViewModel.horarios) { item ->
                        BotonHorario(
                            nombre = item.nombre,
                            onNavToHorario = onNavToHorario,
                            isDeleting = isDeleting,
                            isAlertVisible = isAlertVisible,
                            nombreHorarioActual = nombreHorarioActual,
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RoundedButton(
                            modifier = modifier.padding(vertical = 12.dp, horizontal = 5.dp),
                            text = "Agregar Horario",
                            color = if (userDataViewModel.userData.value.numHorarios < 3) colorEnabeButton
                                else colorDisableButton,
                            width = 130.dp,
                            height = 40.dp,
                            fontSize = 13.sp,
                            onClick = {
                                if (userDataViewModel.userData.value.numHorarios < 3) {
                                    isAnimationVisible.value = true
                                }else{
                                    //Mensaje de MAX 3 horarios
                                    println("Ha alcanzado la cantidad maxima de horarios: 3")
                                    Toast.makeText(context,R.string.limit_Schedule, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                        RoundedButton(
                            modifier = modifier.padding(vertical = 12.dp, horizontal = 5.dp),
                            text = if (isDeleting.value && userDataViewModel.userData.value.numHorarios > 0) "Cancelar" else "Eliminar Horario",
                            color = if (userDataViewModel.userData.value.numHorarios > 0) ButtonDefaults.buttonColors(Color.Red)
                            else ButtonDefaults.buttonColors(Color.Red.copy(0.4f)),
                            width = 130.dp,
                            height = 40.dp,
                            fontSize = 13.sp,
                            onClick = {
                                if (userDataViewModel.userData.value.numHorarios > 0) {
                                    isDeleting.value = !isDeleting.value
                                }
                            }
                        )
                    }
                }
            }
        }
        if (isAlertVisible.value){
            AlertaConformacion(
                nombreHorario = nombreHorarioActual.value,
                isAlertVisible = isAlertVisible,
                userDataViewModel = userDataViewModel
            )
        }
    }
}


@Composable
fun NoLoginScreen(
    modifier: Modifier,
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.default_image), contentDescription = "")
        Text(
            modifier = modifier.padding(top = 20.dp),
            text = "Ingresa a una cuenta para poder acceder a esta función",
            style = MaterialTheme.typography.h6.copy(
                color = colorResource(
                    id = R.color.azulOscuroInstitucional
                )
            ),
            textAlign = TextAlign.Center
        )
        ClickableText(
            modifier = modifier.padding(top = 10.dp),
            style = TextStyle(color = colorResource(id = R.color.azulOscuroInstitucional)),
            text = buildAnnotatedString { append("Ya tengo una cuenta. ")
            withStyle(style = SpanStyle(color= colorResource(id = R.color.azulClaroInstitucional),
                                        fontWeight = FontWeight.Bold)
            )
            {
                append("Inicia Sesion")
            } },
            onClick = {
                          onLogin()
                      }
        )
        ClickableText(
            modifier = modifier.padding(top = 10.dp),
            style = TextStyle(color = colorResource(id = R.color.azulOscuroInstitucional)),
            text = buildAnnotatedString {
            append("Aun no tengo una cuenta. ")
            withStyle(style = SpanStyle(color= colorResource(id = R.color.azulClaroInstitucional),
                                        fontWeight = FontWeight.Bold)
            )
            {
                append("Crear una cuenta")
            } },
                      onClick = {
                          onRegister()
                      }
        )
    }
}



@ExperimentalAnimationApi
@Composable
private fun AgregarHorario (
    visibility : MutableState<Boolean>,
    userId : String,
    userDataViewModel: UserDataViewModel
){

    val focusManager = LocalFocusManager.current
    val text = remember{mutableStateOf("")}
    val maxChar = remember { mutableStateOf(20)}
    val isErrorVisible = remember { mutableStateOf(false)}
    val errorText = remember { mutableStateOf("")}

    maxChar.value = 20 - if (text.value.length < 0) 0
    else text.value.length
    if (maxChar.value < 0) maxChar.value = 0

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, bottom = 40.dp)
        .padding(horizontal = 8.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Ingresa un nombre para tu horario:",
            style = TextStyle(
                color = colorResource(id = R.color.azulOscuroInstitucional),
                fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                fontSize = 20.sp
            )
        )
        TransparentTextField(
            textFieldValue = text,
            maxChar = 20,
            textLabel = "Nombre:    (${maxChar.value})",
            keyboardType = KeyboardType.Text,
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
            imeAction = ImeAction.Done,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedMediaButton(
                text = "Cancelar",
                onClick = { visibility.value = false }, 
                buttonColor = colorResource(id = R.color.azulOscuroInstitucional),
                width = 150.dp,
                heigth = 40.dp,
                textStyle = TextStyle(fontSize = 12.sp)
            )
            Divider(Modifier.width(15.dp), color = Color.Transparent)
            OutlinedMediaButton(
                text = "Aceptar",
                onClick = {
                    errorText.value = ""
                    for (horario in userDataViewModel.horarios){
                        if (horario.nombre == text.value){
                            errorText.value = "Ya existe un horario con ese nombre."
                        }
                    }
                    if (text.value.length < 3){
                        errorText.value = "El nombre debe tener al menos 3 caracteres."
                    }else if (text.value.endsWith(" ") || text.value.contains("&") || text.value.contains("$")){
                        errorText.value = "El nombre no puede terminar con espacio (' ') ni tener los caracteres: [&, $]"
                    }
                    if (errorText.value == ""){
                        crearHorario(nombre = text.value, userId = userId, userDataViewModel = userDataViewModel)
                        userDataViewModel.agregarNombreHorario(text.value)
                        visibility.value = false
                    }else{
                        isErrorVisible.value = true
                    }
                },
                buttonColor = colorResource(id = R.color.azulOscuroInstitucional),
                width = 150.dp,
                heigth = 40.dp,
                textStyle = TextStyle(fontSize = 12.sp)
            )
        }
        AnimatedVisibility(visible = isErrorVisible.value) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .wrapContentSize(align = Alignment.Center),
                text = errorText.value,
                style = TextStyle(
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
private fun AlertaConformacion(
    nombreHorario : String,
    isAlertVisible : MutableState<Boolean>,
    userDataViewModel: UserDataViewModel
) {
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(8)),
        onDismissRequest = {},
        title = {
            Text(
                text = "Borrar Horario",
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
                text = "¿Seguro que desea borrar el horario '$nombreHorario'?",
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
                    eliminarHorario(nombre = nombreHorario, userDataViewModel = userDataViewModel)
                    isAlertVisible.value = false
                })
                {
                    Text(
                        text = "Borrar",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontFamily = sansPro,
                        )
                    )
                }
                Divider(modifier = Modifier.width(8.dp), color = Color.Transparent)
                TextButton(onClick = { isAlertVisible.value = false }) {
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

@ExperimentalAnimationApi
@Composable
private fun BotonHorario(
    nombre : String,
    onNavToHorario : (String) -> Unit,
    isDeleting : MutableState<Boolean>,
    isAlertVisible : MutableState<Boolean>,
    nombreHorarioActual : MutableState<String>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
            .clickable { onNavToHorario(nombre) },
        border = BorderStroke(width = 1.dp, color = primaryColorCustom),
        shape = RoundedCornerShape(10),
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AnimatedVisibility(visible = isDeleting.value) {
                IconButton(
                    modifier = Modifier.padding(end = 20.dp),
                    onClick = {
                        isAlertVisible.value = true
                        nombreHorarioActual.value = nombre
                    }
                )
                {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Rounded.HighlightOff,
                        contentDescription = "",
                        tint = Color.Red
                    )
                }
            }
            Row(
                modifier = Modifier.padding(start = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = nombre,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.CenterEnd)
                        .padding(end = 5.dp)
                        .size(40.dp),
                    imageVector = Icons.Rounded.ChevronRight ,
                    contentDescription = "",
                    tint = colorResource(id = R.color.azulOscuroInstitucional),
                )
            }
        }

    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun TestBotonHorario() {
    val isVisible = remember { mutableStateOf(true) }
    val isAlertaVisible = remember { mutableStateOf(false) }
    val nombre = remember { mutableStateOf("") }
    BotonHorario(nombre = "Horario", onNavToHorario = {}, isDeleting = isVisible, isAlertVisible = isAlertaVisible, nombreHorarioActual = nombre )
}