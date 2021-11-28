package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MiCuentaOption(navController: NavController) {
    val titulos = remember{ mutableStateOf("Mi Cuenta") }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

LoginScreen(navController = navController)

//    HorariosBUAPTheme() {
//        Scaffold(modifier= Modifier
//            .fillMaxSize(),
//        ) {
//            Text(text = "Ventana de Mi cuenta")
//        }
//    }
}