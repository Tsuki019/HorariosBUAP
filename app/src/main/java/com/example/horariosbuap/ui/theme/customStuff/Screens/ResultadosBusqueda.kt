package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.*
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@ExperimentalAnimationApi
@Composable
fun ResultadosBusqueda(
    datosViewModel: DatosViewModel,
    titulos : MutableState<String>
) {

    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val focusManager = LocalFocusManager.current
    val busqueda = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blanco_fondo))
    ){
        TransparentTextField(
            modifier = Modifier.padding(horizontal = 8.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false
            )
        ){
            if (busqueda.value == ""){
                when(datosViewModel.tipoBusqueda.value){

                    1 -> {
                        titulos.value = "Buscar Profesor"
                        getProfesores(datosViewModel = datosViewModel)
                        item {
                            if (datosViewModel.profesoresState.value){
                                for (datos in datosViewModel.profesores){
                                    CardProfesor(datos = datos)
                                }
                            }
                        }
                    }
                    2 -> {
                        titulos.value = "Buscar Salón"
                        getEdificios(datosViewModel = datosViewModel)
                        item {
                            if (datosViewModel.edificiosState.value){
                                for (datos in datosViewModel.edificios){
                                    CardEdicios(datos = datos)
                                }
                            }
                        }
                    }
                    3 -> {
                        titulos.value = "Buscar Materia"
                    }
                    else -> {}
                }
            }else{
//                buscarProfesor(busqueda)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CardEdicios(
    datos : Edificios?
) {
    val modifier = Modifier.padding(start = 2.dp)
    val expanded = remember{ mutableStateOf(false)}

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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
        ) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.padding(top = 10.dp),
                    contentAlignment = Alignment.Center){
                    Image(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(corner = CornerSize(15)))
                            .width(100.dp)
                            .height(100.dp),
                        painter = rememberImagePainter(data = datos!!.imagen),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = modifier.padding(top = 3.dp),
                        text = "Edificio: ${datos!!.nombre}",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)
                        )
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 3.dp, vertical = 2.dp),
                        thickness = 1.dp,
                        color = colorResource(id = R.color.azulOscuroInstitucional))
                    Text(
                        modifier = Modifier.padding(end = 2.dp),
                        text = datos.descripcion,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        ),
                        textAlign = TextAlign.Justify
                    )
                }
            }
            AnimatedVisibility(visible = expanded.value) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    text = "Expandido", textAlign = TextAlign.Center)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                if (expanded.value){
                    Icon(imageVector = Icons.Rounded.ExpandLess, contentDescription = "")
                }else{
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "")
                }
            }
        }

    }
}

@Composable
fun CardProfesor(
    datos : Profesores?
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
                text = datos!!.nombre,
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)
                    )
            )
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
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro))
                    )
            )
            if (datos.cubiculo != ""){
                Text(
                    modifier = modifier,
                    text = datos.cubiculo,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulClaroInstitucional),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro))
                    )
                )
            }
            if (datos.correo2 != ""){
                SelectionContainer (
                    modifier = modifier
                ) {
                    Column() {
                        Text(
                            text = datos.correo,
                            style = TextStyle(
                                color = colorResource(id = R.color.azulClaroInstitucional),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.source_sans_pro))
                            )
                        )
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = datos.correo2,
                            style = TextStyle(
                                color = colorResource(id = R.color.azulClaroInstitucional),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.source_sans_pro))
                            )
                        )
                    }
                }
            }else{
                SelectionContainer(
                    modifier = modifier.padding(bottom = 5.dp),
                ) {
                    Text(
                        text = datos.correo,
                        style = TextStyle(
                            color = colorResource(id = R.color.azulClaroInstitucional),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro))
                        )
                    )
                }

            }
        }
    }
}