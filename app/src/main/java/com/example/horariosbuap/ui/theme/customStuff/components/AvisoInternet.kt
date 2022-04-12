package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.ui.theme.customStuff.sansPro

@Composable
fun AvisoInternet(
    avisoVisible : MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.error.copy(alpha = 0.8f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "Parece que no tienes acceso a internet, los cambios que hagas pueden no guardarse correctamente.",
                color = Color.White,
                textAlign = TextAlign.Justify,
                fontFamily = sansPro,
                fontSize = 16.sp
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                IconButton(
                    onClick = { avisoVisible.value = !avisoVisible.value},
                ) {
                    Icon(
                        modifier = Modifier
                            .heightIn(min = 50.dp, max = 150.dp)
                            .widthIn(min = 50.dp, max = 150.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}