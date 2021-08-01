package com.example.horariosbuap.ui.theme.customStuff

import android.graphics.drawable.DrawableContainer
import android.service.autofill.OnClickAction
import android.view.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector



sealed class Screen(
    val id: String,
    val title: String,
    val image: ImageVector)
{
    object Noticias:Screen("noticias", "Noticias", Icons.Rounded.Try)
    object Buscar:Screen("buscar", "Buscar", Icons.Rounded.Search)
    object Horario:Screen("horarios", "Mi Horario", Icons.Rounded.CalendarToday)
    object Libre:Screen("libre", "Libre", Icons.Rounded.Star)

    object Items{
        val list = listOf(
            Noticias, Buscar, Horario, Libre
        )
    }
}

