package com.example.horariosbuap.ui.theme.customStuff

import android.graphics.drawable.DrawableContainer
import android.service.autofill.OnClickAction
import android.view.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.ActivityNavigator
import com.example.horariosbuap.MainDestinations


sealed class Screen(
    val id: String,
    val title: String,
    val image: ImageVector,
    val destination: String
    )
{
    object Noticias:Screen("noticias", "Noticias", Icons.Rounded.Try, MainDestinations.NEWS_ROUTE)
    object Buscar:Screen("buscar", "Buscar", Icons.Rounded.Search, MainDestinations.SEARCH_ROUTE)
    object Horario:Screen("horarios", "Mi Horario", Icons.Rounded.CalendarToday, MainDestinations.SCHEDULE_ROUTE)
    object Libre:Screen("libre", "Libre", Icons.Rounded.Star, MainDestinations.FREE_ROUTE)

    object Items{
        val list = listOf(
            Noticias, Buscar, Horario, Libre
        )
    }
}

