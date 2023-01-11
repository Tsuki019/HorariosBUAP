package com.prvt.horariosbuap.ui.theme.customStuff.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.material.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.prvt.horariosbuap.R
import com.prvt.horariosbuap.ui.theme.*
import com.prvt.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.prvt.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro
import com.prvt.horariosbuap.ui.theme.dataBase.*
import com.prvt.horariosbuap.viewmodel.LoginViewModel
import com.prvt.horariosbuap.viewmodel.UserDataViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.prvt.horariosbuap.core.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MiCuentaOption(
    viewModel: LoginViewModel,
    activity: Activity,
    onSignOut: () -> Unit,
    userDataViewModel: UserDataViewModel
) {
    val user = Firebase.auth
    val cambiarImagenState = remember { mutableStateOf(false)}
    val progressBarState = remember {mutableStateOf(false)}
    val tiempos = TimeDifferences

    if(userDataViewModel.userData.value.fechaCambioNombre == Date(1654059600000) && user.currentUser != null){
        getUserData(userDataViewModel,user.currentUser!!.uid)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
     LazyColumn(
         modifier = Modifier
             .fillMaxWidth()
             .padding(horizontal = 5.dp),
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
         item { NombrePublico(viewModel = viewModel, userDataViewModel = userDataViewModel, activity = activity, tiempos) }
         item { Divisor()}
         item { Correo(viewModel = viewModel) }
         item { Divisor() }
         item {if (userDataViewModel.userData.value.provider != "GOOGLE") Contrasena(viewModel = viewModel, activity = activity) }
         item { SalirDeCuenta(viewModel = viewModel, onSignOut = onSignOut, userDataViewModel = userDataViewModel) }
     }
    }
}

@Composable
fun TituloSeccion(text : String) {

    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = text,
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = sansPro,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
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
            progressIndicatorColor = light_blue1,
            color = ButtonDefaults.buttonColors(backgroundColor = light_blue1),
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
    userDataViewModel: UserDataViewModel,
    activity: Activity,
    tiempos : TimeDifferences
) {
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
                        cursorColor = light_blue2,
                        focusedIndicatorColor = light_blue2,
                        focusedLabelColor = light_blue2,
                        unfocusedLabelColor = MaterialTheme.colors.primaryVariant,
                        unfocusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                        textColor = MaterialTheme.colors.primary
                    )
                )
                RoundedButton(
                    modifier = Modifier.padding(2.dp),
                    text = "Guardar",
                    width = 90.dp,
                    height = 40.dp,
                    fontSize = 13.sp,
                    color = ButtonDefaults.buttonColors(backgroundColor = light_blue1),
                    progressIndicatorColor = light_blue1,
                    displayProgressBar = progressBarState.value,
                    onClick = {

                        if (nameValue.value != viewModel.state.value.name){
                            if (nameValue.value.length in 4..15){

                                val cambiadoEn = userDataViewModel.userData.value.fechaCambioNombre.time.div(1000L).let {
                                    tiempos.getTimeLimitDays(it.toInt()) }


                                if (cambiadoEn == -1000){
                                    Toast.makeText(activity, "Error al comprobar las fechas, compruebe su conexión a internet", Toast.LENGTH_SHORT).show()
                                }else if (cambiadoEn - 30 >= 0){
                                    corutineScope.launch {
                                        progressBarState.value = true
                                        UpdateUserName(
                                            newName = nameValue.value,
                                            viewModel = viewModel,
                                            activity = activity
                                        )
                                        userDataViewModel.actualizarFechaCambioNombre(
                                            obternerFechaActual().seconds * 1000L
                                        )
                                        delay(1000)
                                        progressBarState.value = false
                                    }
                                }else{
                                    Toast.makeText(activity, "Solo puedes cambiar tu nombre cada 30 días. Faltan: ${-(cambiadoEn - 30)} días", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(activity, "El nombre debe tener de 4 a 15 caracteres", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(activity, "Ya tiene ese nombre", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                text = "Solo puedes cambiar tu nombre cada 30 días y debe de tener entre 4 y 15 caracteres",
                style = TextStyle(
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Justify,
                    fontSize = 12.sp,
                    fontFamily = sansPro,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Composable
fun Correo(viewModel: LoginViewModel) {

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
                color = MaterialTheme.colors.primary)
            Text(
                text = viewModel.state.value.email,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold)
        }
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
                        tint = light_blue1
                    )
                }
            },
            visualTransformation = if (currentPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
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
                        tint = light_blue1
                    )
                }
            },
            visualTransformation = if (newPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
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
                        tint = light_blue1
                    )
                }
            },
            visualTransformation = if (confirmNewPasswordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
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
                progressIndicatorColor = MaterialTheme.colors.secondary,
                color = ButtonDefaults.buttonColors(backgroundColor = light_blue1),
                onClick = {
                    corutineScope.launch {
                        progressBarState.value = true
                        val user =Firebase.auth.currentUser
                        if (user != null){
                            if (!currentPassword.value.isNullOrEmpty()){    //Comprueba si la contrasena ingresada no es null ni vacia
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
                            }else{
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
                .background(Color.Transparent)
                .padding((16.dp)),
                        onDismissRequest = {},
                        title = {
                            Text(text = "Salir de la cuenta",
                                 style = TextStyle(
                                     color = MaterialTheme.colors.primary,
                                     fontSize = 20.sp,
                                     fontWeight = FontWeight.Bold)
                            )
                        },
                        text =  {
                            Text(text = "¿Seguro que desea salir de su cuenta?",
                                 style = TextStyle(color = MaterialTheme.colors.primaryVariant,
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
                                    Text(text = "Cancelar", style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.primaryVariant))
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
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
                    color = MaterialTheme.colors.background.copy(alpha = 0.7f),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary)
                ) {
                    IconButton(onClick = {
                        SelectImage(activity)
                    })
                    {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
                Text(text = "La imagen debe pesar menos de 1MB", color = MaterialTheme.colors.primary, fontSize = 10.sp)
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
                    color = ButtonDefaults.buttonColors(backgroundColor = light_blue1),
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
            progressIndicatorColor = MaterialTheme.colors.secondary,
            color = ButtonDefaults.buttonColors(backgroundColor = light_blue1),
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

private fun obternerFechaActual(): com.google.firebase.Timestamp{
    return com.google.firebase.Timestamp.now()
}