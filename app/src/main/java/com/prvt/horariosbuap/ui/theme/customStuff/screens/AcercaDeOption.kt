package com.prvt.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro

@Composable
fun AcercaDeOption() {

    val text =
        "La aplicación presente fue creada por el alumno Pablo Rafael Vidal Torres utilizando Jetpack Compose y con motivo del proceso de las practicas profecionales bajo el asesoramiento del profesor Mario Anzures García. MiFacultad BUAP tiene la finalidad agrupar la información primordial que un alumno de la Facultad de Ciencias de la Computación pueda necesitar en el transcurso de su día como lo son los datos completos de las materias, los profesores, directores y coordinadores, edificios, salones y laboratorios, además, se agregará una herramienta para que los alumnos puedan crear hasta 3 horarios buscando las materias que desean agregar por nombre, NRC o profesor que imparte la materia, y así tener de manera gráfica varias alternativas para inscripción de materias o tener un registro de sus materias ya inscritas."


    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
    ){
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    fontFamily = sansPro,
                    color = MaterialTheme.colors.primary
                ),
                fontSize = 18.sp
            )
        }
    }
}