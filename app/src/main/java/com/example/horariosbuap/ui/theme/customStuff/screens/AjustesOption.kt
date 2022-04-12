package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.horariosbuap.core.Preferences
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.viewmodel.UserDataViewModel

@Composable
fun AjustesOption(
    userDataViewModel: UserDataViewModel
) {
    val prefs = Preferences(LocalContext.current)
    var isDarkTheme = userDataViewModel.isDarkTheme.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OpcionItem(texto =
        if (!isDarkTheme) "Activar tema oscuro" else "Activar tema claro"
        ) {
            Row() {
                ToggleButtonTheme(
                    isDarkTheme = isDarkTheme,
                    userDataViewModel = userDataViewModel,
                    prefs = prefs
                )
            }
        }
    }
}

fun cambiarTema(
    isDarkTheme: Boolean,
    userDataViewModel: UserDataViewModel,
    prefs : Preferences
){
    prefs.saveTheme(!isDarkTheme)
    userDataViewModel.setTheme(isDarkTheme = !isDarkTheme)
}

@Composable
fun OpcionItem(
    texto: String,
    complemento :@Composable () -> Unit
) {

    val modifierRow = Modifier
        .fillMaxWidth()
        .height(50.dp)

    Row(
        modifier = modifierRow,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier =Modifier.fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ){
            TextoOpcion(texto = texto)
        }
        Box(
            modifier =Modifier.fillMaxWidth().fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd
        ){
            complemento.invoke()
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
        color = MaterialTheme.colors.onSurface)
}

@Composable
fun TextoOpcion(
    texto : String
) {

    Text(
        text = texto,
        textAlign = TextAlign.Left,
        color = MaterialTheme.colors.primary,
        fontFamily = sansPro,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun ToggleButtonTheme(
    isDarkTheme : Boolean,
    userDataViewModel: UserDataViewModel,
    prefs : Preferences
) {

    val icono =
        if (isDarkTheme) Icons.Rounded.LightMode
        else Icons.Rounded.DarkMode

    val border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colors.primary)

    OutlinedButton(
        onClick = {
            prefs.saveTheme(!isDarkTheme)
            userDataViewModel.setTheme(isDarkTheme = !isDarkTheme) },
        border = border,
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.8f),
            contentColor = MaterialTheme.colors.background
        )
    ) {
        Icon(imageVector = icono, contentDescription = "")
    }
}