package com.example.horariosbuap.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun HorariosBUAPTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val DarkColorsTheme = darkColors(
    primary = light_blue1,
    primaryVariant = light_blue2,
    secondary = grey1,
    //secondaryVariant = dark_blue4,
    background = dark_blue1,
    surface = dark_blue3,
    onSurface = dark_blue2
)

private val LightColorsTheme = lightColors(
    primary = dark_blue1,
    primaryVariant = dark_blue2,
    secondary = light_blue1,
    //secondaryVariant = dark_blue2,
    background = grey1,
    surface = Color.White,
    onSurface = dark_blue2
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorsTheme
    } else {
        LightColorsTheme
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}