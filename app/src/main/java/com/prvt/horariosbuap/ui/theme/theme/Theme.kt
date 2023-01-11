package com.prvt.horariosbuap.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro

/**
 * primary => Titulos y textos
 * primaryVariant => focused textfield
 * secondary => links y botones
 * secondaryVariant => iconos del bottomNav
 * background => Fondos
 * surface => background de Carts
 * onSurface => Bordes, dividers y tablas
 **/

private val DarkColorsTheme = darkColors(
    primary = Color.White,
    primaryVariant = grey2,
    secondary = light_blue1,
    secondaryVariant = grey1,
    background = dark_blue1,
    surface = dark_blue3,
    error = Color.Red,
    onPrimary = light_blue1,
    onSecondary = light_blue2,
    onSurface = light_blue2,
    onBackground = dark_blue2
)

@SuppressLint("ConflictingOnColor")
private val LightColorsTheme = lightColors(
    primary = dark_blue1,
    primaryVariant = dark_blue2,
    secondary = light_blue1,
    secondaryVariant = light_blue1,
    background = grey1,
    surface = Color.White,
    error = Color.Red,
    onPrimary = dark_blue1,
    onSecondary = light_blue1,
    onSurface = dark_blue1,
    onBackground = Color.White
)

private val TextTypography = androidx.compose.material.Typography(defaultFontFamily = sansPro)

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
        content = content,
        typography = TextTypography
    )
}