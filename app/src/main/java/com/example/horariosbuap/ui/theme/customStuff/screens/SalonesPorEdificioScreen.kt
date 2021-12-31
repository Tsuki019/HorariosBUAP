package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.BackArrowButtonn
import com.example.horariosbuap.ui.theme.dataBase.Salones
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@ExperimentalAnimationApi
@Composable
fun SalonesPorEdificio(
    salones : ArrayList<Salones?>,
    edificio : String?,
    onBack : () -> Unit
) {
    if(!salones.isEmpty()){
        val pisos = listOf("Sotano", "1er Piso", "2do Piso", "3er Piso", "4to Piso", "5to Piso", "6to Piso")
        var cont = 0
        val mostrador = remember { mutableStateOf(-1) }

        Box(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.blanco_fondo))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackArrowButtonn(onBack = { onBack() })
                    Text(
                        modifier = Modifier
                            .padding(top = 3.dp),
                        text = "Salones en: $edificio",
                        style = TextStyle(
                            color = colorResource(id = R.color.azulOscuroInstitucional),
                            fontWeight = FontWeight.Bold),
                        fontSize = 23.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)
                        )
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars,
                        applyTop = false),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    items(pisos){
                        MostrarSalones(
                            salones = salones,
                            piso = it,
                            edificio = edificio,
                            mostrador = mostrador,
                            indexPiso = cont
                        )
                        cont++
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun MostrarSalones(
    salones : ArrayList<Salones?>,
    piso : String,
    edificio : String?,
    mostrador : MutableState<Int>,
    indexPiso  : Int
) {
    val salonesEdi = arrayListOf<Salones>()


    for(salon in salones){
        if (salon!!.edificio == edificio){
            salonesEdi.add(salon)
        }
    }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val salonesPiso = salonesEdi.filter { it.piso == piso }
        if (!salonesPiso.isEmpty()){

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = piso,
                style = TextStyle(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Divider(
                modifier = Modifier
                    .width(300.dp)
                    .padding(horizontal = 20.dp),
                color = colorResource(id = R.color.azulOscuroInstitucional)
            )
            IconButton(
                modifier = Modifier.padding(vertical = 1.dp),
                onClick = {  if (mostrador.value == indexPiso) mostrador.value = -1 else mostrador.value = indexPiso }
            ) {
                if (mostrador.value != indexPiso)
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "")
                else
                    Icon(imageVector = Icons.Rounded.ExpandLess, contentDescription = "")
            }

            AnimatedVisibility(visible = mostrador.value == indexPiso) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.azulOscuroInstitucional).copy(
                                0.8f
                            )
                        )
                    ) {
                        TableCell(text = "Salon", weight = 0.3f, textColor = Color.White, textSize = 15.sp, fontWeight = FontWeight.Medium)
                        TableCell(text = "Tipo", weight = 0.7f, textColor = Color.White, textSize = 15.sp, fontWeight = FontWeight.Medium)
                    }
                    for (salon in salonesPiso){
                        Row() {
                            TableCell(text = salon.salonId, weight = 0.3f)
                            TableCell(text = salon.tipo, weight = 0.7f)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor : Color = colorResource(id = R.color.azulClaroInstitucional),
    textSize : TextUnit = 13.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, color = colorResource(id = R.color.azulOscuroInstitucional))
            .weight(weight)
            .padding(8.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            color = textColor,
            fontSize = textSize,
            fontFamily = FontFamily(Font(R.font.source_sans_pro)),
            fontWeight = fontWeight
        )
    )
}