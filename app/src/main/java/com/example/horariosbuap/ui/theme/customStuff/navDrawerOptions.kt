package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.ActivityNavigator
import com.example.horariosbuap.MainDestinations

sealed class navDrawerOptions(
    val id: String,
    val opcion: String,
    val icon: ImageVector,
    val destination : String
){

    object Inicio:navDrawerOptions(id = "inicio", opcion = "Menu principal", icon = Icons.Rounded.Home, MainDestinations.NEWS_ROUTE)
    object Cuenta:navDrawerOptions(id = "cuenta", opcion = "Mi cuenta", icon = Icons.Rounded.Person, MainDestinations.ACCOUNT_ROUTE)
    object Ajuste:navDrawerOptions(id = "ajustes", opcion = "Ajustes", icon = Icons.Rounded.Settings, MainDestinations.SETTINGS_ROUTE)
    object Acerca:navDrawerOptions(id = "acerca", opcion = "Acerca de la aplicacion", icon = Icons.Rounded.Info, MainDestinations.ABOUT_ROUTE)
    object Compartir:navDrawerOptions(id = "compartir", opcion = "Compartir", icon = Icons.Rounded.Share, MainDestinations.SHARE_ROUTE)
    object Salir:navDrawerOptions(id = "salir", opcion = "Salir de la aplicacion", icon = Icons.Rounded.DoorBack, MainDestinations.EXIT_ROUTE)

    object Items{
        val list = listOf(
            Inicio,Cuenta, Ajuste, Acerca,Compartir, Salir
        )
    }
}