package com.example.horariosbuap.ui.theme.customStuff.screens

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.material.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.*
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MiCuentaOption(
    viewModel: LoginViewModel,
    activity: Activity,
    onSignOut: () -> Unit,
    userDataViewModel: UserDataViewModel
) {

    val cambiarImagenState = remember { mutableStateOf(false)}
    val progressBarState = remember {mutableStateOf(false)}

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
         item {
             if(cambiarImagenState.value){
                 CambiarImagen(
                     viewModel = viewModel,
                     cambiarImagenState = cambiarImagenState,
                     progressBarState = progressBarState,
                     activity = activity
                 )
             }else{
                 FotoPerfil(viewModel = viewModel, cambiarImagenState = cambiarImagenState)
             }
         }
         item { Divisor() }
         item { NombrePublico(viewModel = viewModel, activity = activity) }
         item { Divisor()}
         item { Correo(viewModel = viewModel) }
         item { Divisor() }
         item { if (userDataViewModel.userData.value.provider != "GOOGLE") Contrasena(viewModel = viewModel, activity = activity) }
         item { SalirDeCuenta(viewModel = viewModel, onSignOut = onSignOut, userDataViewModel = userDataViewModel) }
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
private fun Divisor() {

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp, vertical = 25.dp),
        thickness = 1.dp,
        color = Color.Transparent)
}


@Composable
fun FotoPerfil(
    viewModel: LoginViewModel,
    cambiarImagenState : MutableState<Boolean>,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(modifier= Modifier
            .width(130.dp)
            .height(130.dp)
            .clip(shape = CircleShape),
              painter = if (viewModel.state.value.image == ""){
                  painterResource(id = R.drawable.default_image)
              }else{
                  rememberImagePainter(data = viewModel.state.value.image)
                   },
              contentDescription = "",
              contentScale = ContentScale.FillBounds)
        RoundedButton(
            modifier = Modifier.padding(8.dp),
            text = "Cambiar imagen",
            width = 200.dp,
            height = 40.dp,
            progressIndicatorColor = colorResource(id = R.color.azulClaroInstitucional),
            color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
            fontSize = 15.sp,
            onClick = {
                cambiarImagenState.value = true
                viewModel.tempImage.value = Uri.EMPTY
            }
        )
    }

}

@Composable
fun NombrePublico(
    viewModel: LoginViewModel,
    activity: Activity
) {
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val maxChar: Int? = null
    val nameValue = remember { mutableStateOf(viewModel.state.value.name)}
    val progressBarState = remember { mutableStateOf(false)}
    val corutineScope = rememberCoroutineScope()

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
                    color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
                    progressIndicatorColor = colorResource(id = R.color.azulClaroInstitucional),
                    displayProgressBar = progressBarState.value,
                    onClick = {
                        if (nameValue.value != viewModel.state.value.name){
                            if ( nameValue.value.length >= 4){
                                corutineScope.launch {
                                    progressBarState.value = true
                                    UpdateUserName(
                                        newName = nameValue.value,
                                        viewModel = viewModel,
                                        activity = activity
                                    )
                                    delay(1000)
                                    progressBarState.value = false
                                }
                            }else{
                                Toast.makeText(activity, "El nombre debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(activity, "Ya tiene ese nombre", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Correo(viewModel: LoginViewModel) {

//    val focusManager = LocalFocusManager.current
//    val email = rememberSaveable{ mutableStateOf("")}
//    val confirmationEmail = rememberSaveable{ mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)) {

        TituloSeccion(text = "E-mail")

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)) {
            Text(
                text = "Correo utilizado:",
                fontSize = 14.sp,
                color = Color.White)
            Text(
                text = viewModel.state.value.email,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
        }

//        TransparentTextField(
//            textFieldValue = email,
//            textLabel = "Nuevo correo",
//            keyboardType = KeyboardType.Email,
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    focusManager.clearFocus(
//                        //(proceso para cambiar email)
//                    )
//                }
//            ),
//            imeAction = ImeAction.Done,
//            focusColor = colorResource(id = R.color.azulClaroInstitucional),
//            unFocusedColor = Color.White,
//            textColor = Color.White
//        )

//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally)
//        {
//            RoundedButton(
//                text = "Confirmar cambio",
//                width = 250.dp,
//                height = 40.dp,
//                fontSize = 15.sp,
//                color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
//                onClick = {}
//            )
//        }
    }

}

@Composable
fun Contrasena(
    viewModel: LoginViewModel,
    activity: Activity
){
    val currentPassword = rememberSaveable{ mutableStateOf("")}
    val newPassword = rememberSaveable{ mutableStateOf("")}
    val confirmNewPassword = rememberSaveable{ mutableStateOf("")}
    val focusManager = LocalFocusManager.current
    val progressBarState = remember { mutableStateOf(false)}
    val corutineScope = rememberCoroutineScope()

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
                displayProgressBar = progressBarState.value,
                progressIndicatorColor = colorResource(id = R.color.azulClaroInstitucional),
                color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
                onClick = {
                    corutineScope.launch {
                        progressBarState.value = true
                        val user =Firebase.auth.currentUser
                        if (user != null){
                            val credential = EmailAuthProvider
                                .getCredential(user.email!!, currentPassword.value)
                            user.reauthenticate(credential)
                                .addOnSuccessListener {

                                    val error =
                                            if (confirmNewPassword.value != newPassword.value) {
                                                R.string.error_incorrectly_repeated_password
                                            }else if (newPassword.value.length < 6){
                                                R.string.error_length_password
                                            }else null

                                    if (error == null){
                                        UpdateUserPassword(
                                            newPassword = newPassword.value,
                                            viewModel = viewModel,
                                            activity = activity,
                                            user = user
                                        )
                                        currentPassword.value = ""
                                        newPassword.value = ""
                                        confirmNewPassword.value = ""
                                        focusManager.clearFocus()
                                    }else{
                                        progressBarState.value = false
                                        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                                    }
                                }.addOnFailureListener{
                                    progressBarState.value = false
                                    Toast.makeText(activity, R.string.error_current_password, Toast.LENGTH_SHORT).show()
                                }
                        }
                        delay(1000)
                        progressBarState.value = false
                    }
                }
            )
        }
    }
}

@Composable
fun SalirDeCuenta(viewModel: LoginViewModel, onSignOut : () -> Unit, userDataViewModel: UserDataViewModel) {
    val exit = remember{ mutableStateOf(false)}

    Column (
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        RoundedButton(
            text = "Salir de la Cuenta",
            color = ButtonDefaults.buttonColors(Color.Red),
            onClick = {
                exit.value = true
            }
        )

        if (exit.value){
            AlertDialog(modifier = Modifier
                .background(Color.White)
                .padding((16.dp)),
                        onDismissRequest = {},
                        title = {
                            Text(text = "Salir de la cuenta",
                                 style = TextStyle(
                                     color = MaterialTheme.colors.onSurface,
                                     fontSize = 20.sp,
                                     fontWeight = FontWeight.Bold)
                            )
                        },
                        text =  {
                            Text(text = "¿Seguro que desea salir de su cuenta?",
                                 style = TextStyle(color = MaterialTheme.colors.onSurface,
                                                   fontSize = 16.sp)
                            )
                        },
                        buttons = {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                horizontalArrangement = Arrangement.End)
                            {
                                TextButton(onClick = {
                                    LogOutUser(viewModel = viewModel, userDataViewModel = userDataViewModel)
                                    exit.value = false
                                    onSignOut()
                                }
                                ){
                                    Text(text = "Salir", style = MaterialTheme.typography.button.copy(Color.Red))
                                }
                                TextButton(onClick = {
                                    exit.value = false
                                }
                                ) {
                                    Text(text = "Cancelar", style = MaterialTheme.typography.button.copy(color = colorResource(id = R.color.azulOscuroInstitucional)))
                                }
                            }
                        }
            )
        }
    }
}

@Composable
fun CambiarImagen(
    viewModel: LoginViewModel,
    cambiarImagenState: MutableState<Boolean>,
    progressBarState: MutableState<Boolean>,
    activity: Activity
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        if (viewModel.tempImage.value == Uri.EMPTY){
            Column (
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                Surface(
                    modifier = Modifier.size(130.dp),
                    shape = CircleShape,
                    color = colorResource(id = R.color.BlancoTransparente),
                    border = BorderStroke(width = 1.dp, color = Color.White)
                ) {
                    IconButton(onClick = {
                        SelectImage(activity)
                    })
                    {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
                Text(text = "La imagen debe pesar menos de 1MB", color = Color.White, fontSize = 10.sp)
            }
        } else{
            Column(verticalArrangement = Arrangement.spacedBy(15.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier= Modifier
                        .width(130.dp)
                        .height(130.dp)
                        .clip(shape = CircleShape),
                    painter = rememberImagePainter(data = viewModel.tempImage.value),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds)

                RoundedButton(
                    text = "Elegir Imagen",
                    fontSize = 10.sp,
                    color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
                    width = 120.dp,
                    height = 30.dp,
                    onClick = {
                        SelectImage(activity)
                    }
                )
            }

        }

        RoundedButton(
            modifier = Modifier.padding(8.dp),
            text = "Guardar Imagen",
            width = 200.dp,
            height = 40.dp,
            displayProgressBar = progressBarState.value,
            progressIndicatorColor = colorResource(id = R.color.azulClaroInstitucional),
            color = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.azulClaroInstitucional)),
            fontSize = 15.sp,
            onClick = {
                coroutineScope.launch {
                    progressBarState.value = true
                    UpdateUserAvatar(viewModel = viewModel, cambiarImagenState = cambiarImagenState, progressBarState = progressBarState)
                }
            }
        )
    }

}