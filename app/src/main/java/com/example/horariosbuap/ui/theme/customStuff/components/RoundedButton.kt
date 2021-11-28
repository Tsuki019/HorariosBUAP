package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import org.w3c.dom.Text


@Composable
fun RoundedButton(
    color: ButtonColors = ButtonDefaults.buttonColors(),
    modifier: Modifier = Modifier,
    text: String,
    displayProgressBar : Boolean = false,
    onClick: () -> Unit
) {

    if (!displayProgressBar){
        Button(
            modifier = modifier
                .width(280.dp)
                .height(50.dp),
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = color
        ) {
           Text(
               text = text,
           style = MaterialTheme.typography.h6.copy(color = Color.White))
        }
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = colorResource(id = R.color.azulOscuroInstitucional),
            strokeWidth = 6.dp
        )
    }
}