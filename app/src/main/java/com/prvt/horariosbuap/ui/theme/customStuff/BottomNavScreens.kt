package com.prvt.horariosbuap.ui.theme.customStuff

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.prvt.horariosbuap.MainDestinations


sealed class BottomNavScreens(
    val id: String,
    val title: String,
    val image: ImageVector,
    val destination: String
    )
{
    object Noticias:BottomNavScreens("noticias", "Noticias", Icons.Rounded.Try, MainDestinations.NEWS_ROUTE)
    object Buscar:BottomNavScreens("buscar", "Buscar", Icons.Rounded.Search, MainDestinations.SEARCH_ROUTE)
    object Horario:BottomNavScreens("horarios", "Mi Horario", Icons.Rounded.CalendarToday, MainDestinations.SCHEDULE_ROUTE)
    //object Libre:BottomNavScreens("libre", "Libre", Icons.Rounded.Star, MainDestinations.FREE_ROUTE)

    object Items{
        val list = listOf(
            Noticias, Buscar, Horario
        )
    }
}

