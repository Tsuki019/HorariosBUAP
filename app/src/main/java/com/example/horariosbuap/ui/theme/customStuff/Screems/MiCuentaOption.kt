package com.example.horariosbuap.ui.theme.customStuff.Screems

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar

@Composable
fun MiCuentaOption() {
    val titulos = remember{ mutableStateOf("Mi Cuenta") }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    HorariosBUAPTheme() {
        Scaffold(modifier= Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azulClaroInstitucional)),
        ) {
            Text(text = "Ventana de Mi cuenta")
        }
    }
}