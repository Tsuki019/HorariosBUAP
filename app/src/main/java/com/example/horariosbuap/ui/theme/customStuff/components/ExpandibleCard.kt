package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.dataBase.MateriaTabla

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandibleCard(
    modifier: Modifier = Modifier,
    text : String,
    textColor : Color = colorResource(id = R.color.azulOscuroInstitucional),
    backgroundColor : Color = Color.White,
    materias : List<MateriaTabla>
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(CornerSize(10.dp))
    ) {
        var expanded = remember{ mutableStateOf(false)}
        
        Column(modifier = modifier
            .fillMaxWidth()
            .clickable { expanded.value = !expanded.value }
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h5.copy(color = textColor, fontWeight = FontWeight.Bold)
            )
            AnimatedVisibility(visible = expanded.value) {
                Column(modifier = modifier.fillMaxWidth()) {
                    for (materia in materias){
                        if (materia.dia == text){
                            Row(modifier = modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.width(100.dp)) {
                                    Text(text = materia.entrada.toString())
                                    Text(text = materia.salida.toString())
                                }
                                Text(text = materia.idMateria)
                            }
                        }
                    }
                }

            }

        }



    }
}