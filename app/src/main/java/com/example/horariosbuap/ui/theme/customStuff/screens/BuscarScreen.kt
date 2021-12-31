package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.dataBase.Profesores
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues


@Composable
fun BuscarScreen(
    onSearchProfesor : () -> Unit,
    onSearchSalon : () -> Unit,
    onSearchMateria : () -> Unit,
) {

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blanco_fondo))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .padding(top = 10.dp),
        ) {

            Text(
                modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(),
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 30.sp
                ),
                textAlign = TextAlign.Center,
                text = "¿Qué buscamos hoy?")
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = false
                )
            ){

                item { SeccionBusqueda(
                    painter = painterResource(id = R.drawable.ic_profesores),
                    campo = "Profesores",
                    onClick = {
                        onSearchProfesor()
                    }
                )}
                item { SeccionBusqueda(
                    painter = painterResource(id = R.drawable.ic_edificios),
                    campo = "Salones",
                    onClick = { onSearchSalon() }
                ) }
                item { SeccionBusqueda(
                    painter = painterResource(id = R.drawable.ic_materias),
                    campo = "Materias",
                    onClick = { onSearchMateria() }
                ) }
            }
        }
    }

}

@Composable
fun SeccionBusqueda(
    painter: Painter,
    campo : String,
    onClick : () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .height(130.dp)
            .clickable(enabled = true) { onClick() },
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.azulOscuroInstitucional)),
        elevation = 5.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(corner = CornerSize(15)))
                    .width(100.dp)
                    .height(100.dp),
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = campo,
                style = MaterialTheme.typography.h5.copy(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Preview
@Composable
fun testBuscar() {
    val onSearchProfesor = {}
    val onSearchSalon = {}
    val onSearchMateria = {}

    BuscarScreen(onSearchMateria = onSearchMateria, onSearchProfesor = onSearchProfesor, onSearchSalon = onSearchSalon)
}