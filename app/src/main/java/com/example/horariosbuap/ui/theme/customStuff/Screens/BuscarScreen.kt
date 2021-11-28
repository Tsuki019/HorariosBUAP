package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.example.horariosbuap.ui.theme.customStuff.Screen


@Composable
fun BuscarScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    openDrawer: ()->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = remember{ mutableStateOf<Screen>(Screen.Noticias) }

    Scaffold(scaffoldState = scaffoldState)
    {
        Text(text = "Espacio para buscar informacion acerca de los profesores, materias y mostrar la info de los edificios")
    }

}