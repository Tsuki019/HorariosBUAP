package com.prvt.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.prvt.horariosbuap.ui.theme.customStuff.BottomNavScreens

@Composable
fun LibreScreen(
    navController: NavController
//    scaffoldState: ScaffoldState,
//    openDrawer: ()->Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = remember{ mutableStateOf<BottomNavScreens>(BottomNavScreens.Noticias) }

    Scaffold(
//        scaffoldState = scaffoldState
    )
    {
        Text(text = "Espacio libre (no tiene nada asignado por el momento)")
    }

}
