package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector



sealed class Screen(
    val id: String,
    val title: String,
    val image: ImageVector)
{
    object Noticias:Screen("home", "Noticias", Icons.Rounded.Try)
    object Buscar:Screen("buscar", "Buscar", Icons.Rounded.Search)
    object Horario:Screen("horario", "Mi Horario", Icons.Rounded.CalendarToday)
    object Libre:Screen("libre", "Libre", Icons.Rounded.Star)

    object Items{
        val list = listOf(
            Screen.Noticias, Screen.Buscar, Screen.Horario, Screen.Libre
        )
    }
}

