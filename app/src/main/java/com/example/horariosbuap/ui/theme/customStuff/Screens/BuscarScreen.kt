package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.Profesores
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues


@Composable
fun BuscarScreen(
    onSearchProfesor : () -> Unit,
    onSearchSalon : () -> Unit,
    onSearchMateria : () -> Unit,
) {
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val busqueda = rememberSaveable { mutableStateOf("") }
    val profesores = Profesores(nombre = "Nombre Apellido Apellido", puesto = "Profesor", cubiculo = "Edi.2 cub.A2", correo = "profe1@buap.com", correo2 = "profe1correo2@buap.com", data = "profe1 Profesor")

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
            TransparentTextField(
                textFieldValue = busqueda,
                textLabel = "Buscar",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                imeAction = ImeAction.Next,
                focusColor = azulClaro,
                unFocusedColor = azulOscuro,
                trailingIcon = {
                    IconButton(onClick = {
                        //TODO (Proceso de busqueda)
                    })
                    {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            tint = azulOscuro
                        )
                    }
                },
                textColor = azulOscuro
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyTop = false
                )
            ){
                item { Divider(modifier = Modifier.padding(top = 10.dp), color = Color.Transparent) }
                if (busqueda.value == ""){
                    item { SeccionBusqueda(
                        painter = painterResource(id = R.drawable.hatsune_test),
                        campo = "Profesores",
                        onClick = { onSearchProfesor() }
                    )}
                    item { SeccionBusqueda(
                        painter = painterResource(id = R.drawable.default_image),
                        campo = "Salones",
                        onClick = { onSearchSalon() }
                    ) }
                    item { SeccionBusqueda(
                        painter = painterResource(id = R.drawable.image_login),
                        campo = "Clases",
                        onClick = { onSearchMateria() }
                    ) }
                }else{
                    item { PostCardData(datos = profesores) }
                    item { PostCardData(datos = profesores) }
                }
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
        val modifier = Modifier.padding(start = 5.dp)

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

@Composable
fun PostCardData(
    datos : Profesores
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        backgroundColor = Color.White,
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azulOscuroInstitucional)),
        shape = RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        )
    ) {
        val modifier = Modifier.padding(start = 5.dp)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = modifier.padding(top = 3.dp),
                text = datos.nombre,
                style = MaterialTheme.typography.h5.copy(color = colorResource(id = R.color.azulOscuroInstitucional), fontWeight = FontWeight.Bold))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                thickness = 1.dp,
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            Text(
                modifier = modifier,
                text = datos.puesto,
                style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulOscuroInstitucional), fontWeight = FontWeight.Bold))
            if (datos.cubiculo != ""){
                Text(
                    modifier = modifier,
                    text = datos.cubiculo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }
            if (datos.correo2 != ""){
                Text(
                    modifier = modifier,
                    text = datos.correo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = datos.correo2,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }else{
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = datos.correo,
                    style = MaterialTheme.typography.h6.copy(color = colorResource(id = R.color.azulClaroInstitucional)))
            }
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