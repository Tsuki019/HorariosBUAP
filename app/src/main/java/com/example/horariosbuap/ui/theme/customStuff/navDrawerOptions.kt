package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class navDrawerOptions(
    val id: String,
    val opcion: String,
    val icon: ImageVector
){

    object Cuenta:navDrawerOptions(id = "cuenta", opcion = "Mi cuenta", icon = Icons.Rounded.Person)
    object Ajuste:navDrawerOptions(id = "ajustes", opcion = "Ajustes", icon = Icons.Rounded.Settings)
    object Acerca:navDrawerOptions(id = "acerca", opcion = "Acerca de la aplicacion", icon = Icons.Rounded.Info)
    object Compartir:navDrawerOptions(id = "compartir", opcion = "Compartir", icon = Icons.Rounded.Share)
    object Salir:navDrawerOptions(id = "salir", opcion = "Salir de la aplicacion", icon = Icons.Rounded.DoorBack)

    object Items{
        val list = listOf(
            Cuenta, Ajuste, Acerca,Compartir, Salir
        )
    }
}