package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.ui.theme.dataBase.LoginViewModel

@Composable
fun MiCuentaOption(onNavigateToLogin: () -> Unit) {
    val titulos = remember{ mutableStateOf("Mi Cuenta") }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel: LoginViewModel = hiltViewModel()

    Text(text = "Ha ingresado satisfactoriamente")
}