package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.BackArrowButtonn
import com.example.horariosbuap.ui.theme.customStuff.components.RoundedButton
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@Composable
fun ReestablecerContrasena(
    onBack : () -> Unit
) {

    val textCorreo = remember { mutableStateOf("") }
    val progressBar = remember { mutableStateOf(false)}
    val mostrarMensaje = remember { mutableStateOf(false)}
    val mensaje = remember { mutableStateOf("Mensaje")}
    val coroutineScope = rememberCoroutineScope()
    val error = remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth(). padding(bottom = 15 .dp), contentAlignment = Alignment.CenterStart) {
                BackArrowButtonn(
                    modifier = Modifier.wrapContentSize(align = Alignment.TopStart),
                    onBack = { onBack()}
                )
            }
            Text(
                text ="Ingrese el correo electronico que resgistr?? con su cuenta para mandarle el correo de reestablecimiento de su contrase??a:",
                style = TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    textAlign = TextAlign.Justify
                )
            )
            TransparentTextField(
                textFieldValue = textCorreo,
                textLabel = "Correo",
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None,
                keyboardActions = KeyboardActions(),
                imeAction = ImeAction.Done
            )

            RoundedButton(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Mandar Correo",
                displayProgressBar = progressBar.value
            ) {
                ResetPassCorreo(
                    coroutineScope = coroutineScope,
                    progressBar = progressBar,
                    mostrarMensaje = mostrarMensaje,
                    mensaje = mensaje,
                    correo = textCorreo.value,
                    error = error
                )
            }

            AnimatedVisibility(visible = mostrarMensaje.value) {
                Text(
                    text = mensaje.value,
                    style = TextStyle(
                        color = if (!error.value){
                            MaterialTheme.colors.primary
                        }else{
                            MaterialTheme.colors.error},
                        fontSize = 15.sp,
                        fontFamily = sansPro
                    )
                )
            }
        }
    }
}


private fun ResetPassCorreo(
    coroutineScope: CoroutineScope,
    progressBar : MutableState<Boolean>,
    mostrarMensaje : MutableState<Boolean>,
    mensaje : MutableState<String>,
    correo : String,
    error : MutableState<Boolean>
) {
    progressBar.value = true

    if (correo != ""){
        coroutineScope.launch {

            Firebase.auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mensaje.value = "Correo enviado! Verifique su correo electr??nico y de click en el link para reestablecer su contrase??a"
                        error.value = false
                    }else{
                        mensaje.value = "El correo electr??nico que ingres?? no pertenece a nunguna cuenta registrada en esta aplicaci??n"
                        error.value = true
                    }
                    mostrarMensaje.value = true
                }
            progressBar.value = false
        }
    }else{
        mensaje.value = "El campo 'Correo' no debe estar vacio"
        mostrarMensaje.value = true
        progressBar.value = false
        error.value = true
    }



}