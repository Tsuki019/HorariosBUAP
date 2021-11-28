package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme

@Composable
fun SalirOption() {
    val titulos = remember{ mutableStateOf("Mi Cuenta") }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    HorariosBUAPTheme() {
        Scaffold(modifier= Modifier
            .fillMaxSize(),
        ) {
            Text(text = "Ventana de Salir")
        }
    }
}