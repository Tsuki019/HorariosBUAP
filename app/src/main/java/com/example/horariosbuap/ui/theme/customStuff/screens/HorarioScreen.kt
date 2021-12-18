package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.ButtonToggleGroup
import com.example.horariosbuap.ui.theme.customStuff.components.ExpandibleCard
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.dataBase.LoginViewModel
import com.example.horariosbuap.ui.theme.dataBase.DatosViewModel
import com.example.horariosbuap.ui.theme.dataBase.Materias
import com.example.horariosbuap.ui.theme.dataBase.getMaterias
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HorarioScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    datosViewModel: DatosViewModel,
    onAddSubject : () -> Unit
) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val modifier = Modifier

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.blanco_fondo)))
    {

        if(currentUser != null && currentUser.isEmailVerified){

            VerHorariosScreen(modifier = modifier, datosViewModel = datosViewModel, onAddSubject)
        }else{
            NoLoginScreen(
                modifier = modifier,
                onLogin = {navController.navigate(route = MainDestinations.LOGIN_ROUTE) },
                onRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)})
        }
        
    }
}

@Composable
fun VerHorariosScreen(
    modifier: Modifier,
    datosViewModel: DatosViewModel,
    onAddSubject: () -> Unit
) {

    if (datosViewModel.state.isEmpty()){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            RoundedButton(
                modifier = modifier.padding(vertical = 12.dp),
                text = "Agregar Materia",
                color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulOscuroInstitucional)),
                width = 130.dp,
                height = 40.dp,
                fontSize = 13.sp,
                onClick = {
                    onAddSubject()
                })
        }
    }else{
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false)
        ){
            item { ExpandibleCard(text = "Lunes", materias = datosViewModel.state) }
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
