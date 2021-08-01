package com.example.horariosbuap.ui.theme.customStuff.Screems

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.Screen
import kotlinx.coroutines.CoroutineScope

@Composable
fun HorarioScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    openDrawer: ()->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = remember{ mutableStateOf<Screen>(Screen.Noticias) }

    Scaffold(scaffoldState = scaffoldState)
    {
        Text(text = "Espacio en el cual se mostrar la informacion del horario correspondiente al la cuenta logueada")
    }

}