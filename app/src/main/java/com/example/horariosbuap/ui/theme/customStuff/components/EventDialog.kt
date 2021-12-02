package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EventDialog(
    modifier: Modifier = Modifier,
    @StringRes errorMessage: Int,
    onDismiss:(() -> Unit)? = null
) {
    AlertDialog(modifier = modifier
        .background(Color.White)
        .padding((16.dp)),
    onDismissRequest = {onDismiss?.invoke()},
    title = {
        Text(text = "Error",
            style = TextStyle(color = MaterialTheme.colors.onSurface
            ,fontSize = 20.sp
            ,fontWeight = FontWeight.Bold)
            )
            },
    text =  {
        Text(text = LocalContext.current.getString(errorMessage),
            style = TextStyle(color = MaterialTheme.colors.onSurface,
            fontSize = 16.sp)
        )
    },
    buttons = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.End) 
        {
            TextButton(onClick = {onDismiss?.invoke()}) {
                Text(text = "Aceptar", style = MaterialTheme.typography.button)
            }
        }
    }  
    )
    
}