package com.example.horariosbuap.ui.theme.customStuff.Screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.LoginViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun MiCuentaOption(viewModel: LoginViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.azulOscuroInstitucional))) {
     LazyColumn(
         modifier = Modifier
             .fillMaxWidth()
             .padding(5.dp),
         contentPadding = rememberInsetsPaddingValues(
             insets = LocalWindowInsets.current.systemBars,
             applyTop = false)
     ){
         item { FotoPerfil(viewModel = viewModel) }
         item { Divisor() }
         item { cuenta(viewModel = viewModel) }
         item { Divisor()}
         item { correo(viewModel = viewModel) }
         item { Divisor() }
         item { Contrasena() }
     }
    }
}

@Composable
fun TituloSeccion(text : String) {

    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = text,
        style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional))
    )
}

@Composable
fun Divisor() {

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 25.dp),
        thickness = 1.dp,
        color = Color.Transparent)
}

@Composable
fun FotoPerfil(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(modifier= Modifier
            .width(130.dp)
            .height(130.dp)
            .clip(shape = CircleShape),
              painter = if (viewModel.state.value.image == ""){
                  painterResource(id = R.drawable.hatsune_test)
              }else{
                  rememberImagePainter(data = viewModel.state.value.image)
                   },
              contentDescription = "")
        RoundedButton(
            modifier = Modifier.padding(8.dp),
            text = "Cambiar imagen",
            width = 200.dp,
            height = 40.dp,
            color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
            onClick = {}
        )
    }

}

@Composable
fun cuenta(viewModel: LoginViewModel) {

    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
//    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val maxChar: Int? = null
    val nameValue = remember { mutableStateOf(viewModel.state.value.name)}

    Box(modifier = Modifier.fillMaxWidth())
    {
        Column(modifier = Modifier.fillMaxWidth()) {

            TituloSeccion(text = "Nombre Público")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                TextField(
                    modifier = Modifier.width(240.dp),
                    value = nameValue.value.take(maxChar?: 40),
                    onValueChange = {nameValue.value = it},
                    label = {Text(text = "Nombre")},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = azulClaro,
                        focusedIndicatorColor = azulClaro,
                        focusedLabelColor = azulClaro,
                        unfocusedLabelColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        textColor = Color.White
                    )
                )
                RoundedButton(
                    modifier = Modifier.padding(2.dp),
                    text = "Guardar",
                    width = 90.dp,
                    height = 40.dp,
                    fontSize = 13.sp,
                    color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional))
                ) {

                }
            }
        }
    }
}

@Composable
fun correo(viewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current
    val email = rememberSaveable{ mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)) {

        TituloSeccion(text = "Cambiar Correo")

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)) {
            Text(
                text = "Correo Actual:",
                fontSize = 14.sp,
                color = Color.White)
            Text(
                text = viewModel.state.value.email,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
        }

        TransparentTextField(
            textFieldValue = email,
            textLabel = "Nuevo correo",
            keyboardType = KeyboardType.Email,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(
                        //TODO (proceso para cambiar email)
                    )
                }
            ),
            imeAction = ImeAction.Done,
            focusColor = colorResource(id = R.color.azulClaroInstitucional),
            unFocusedColor = Color.White,
            textColor = Color.White
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            RoundedButton(
                text = "Confirmar cambio",
                width = 250.dp,
                height = 40.dp,
                fontSize = 15.sp,
                color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
                onClick = {}
            )
        }
    }

}

@Composable
fun Contrasena()
{
    val currentPassword = rememberSaveable{ mutableStateOf("")}
    val newPassword = rememberSaveable{ mutableStateOf("")}
    val confirmNewPassword = rememberSaveable{ mutableStateOf("")}
    val focusManager = LocalFocusManager.current
    var currentPasswordVisibility by remember {mutableStateOf(false)}
    var newPasswordVisibility by remember {mutableStateOf(false)}
    var confirmNewPasswordVisibility by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp))
    {

        TituloSeccion(text = "Cambiar contraseña")

        TransparentTextField(
            textFieldValue = currentPassword,
            textLabel = "Contraseña actual",
            keyboardType = KeyboardType.Password,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }
            ),
            imeAction = ImeAction.Next,
            trailingIcon = {
                IconButton(onClick = {currentPasswordVisibility = !currentPasswordVisibility})
                {
                    Icon(
                        imageVector = if (currentPasswordVisibility) Icons.Rounded.Visibility
                        else Icons.Rounded.VisibilityOff,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (currentPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            focusColor = colorResource(id = R.color.azulClaroInstitucional),
            unFocusedColor = Color.White,
            textColor = Color.White
        )

        TransparentTextField(
            textFieldValue = newPassword,
            textLabel = "Nueva contraseña",
            keyboardType = KeyboardType.Password,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }
            ),
            imeAction = ImeAction.Next,
            trailingIcon = {
                IconButton(onClick = {newPasswordVisibility = !newPasswordVisibility})
                {
                    Icon(
                        imageVector = if (newPasswordVisibility) Icons.Rounded.Visibility
                        else Icons.Rounded.VisibilityOff,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (newPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            focusColor = colorResource(id = R.color.azulClaroInstitucional),
            unFocusedColor = Color.White,
            textColor = Color.White
        )

        TransparentTextField(
            textFieldValue = confirmNewPassword,
            textLabel = "Confirmar nueva contraseña",
            keyboardType = KeyboardType.Password,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    //TODO (Realizar proceso para cambiar password)
                }
            ),
            imeAction = ImeAction.Done,
            trailingIcon = {
                IconButton(onClick = {confirmNewPasswordVisibility = !confirmNewPasswordVisibility})
                {
                    Icon(
                        imageVector = if (confirmNewPasswordVisibility) Icons.Rounded.Visibility
                        else Icons.Rounded.VisibilityOff,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (confirmNewPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            focusColor = colorResource(id = R.color.azulClaroInstitucional),
            unFocusedColor = Color.White,
            textColor = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            RoundedButton(
                modifier = Modifier.padding(top = 10.dp),
                text = "Guardar nueva contraseña",
                width = 250.dp,
                height = 40.dp,
                fontSize = 15.sp,
                color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
                onClick = {}
            )
        }
    }
}