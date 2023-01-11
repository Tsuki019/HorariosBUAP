package com.prvt.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle


@Composable
fun OutlinedMediaButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    buttonColor: Color,
    width : Dp = 280.dp,
    heigth : Dp = 50.dp,
    textStyle: TextStyle = MaterialTheme.typography.h6.copy(color = buttonColor)
) {
    OutlinedButton(modifier = Modifier
        .width(width)
        .height(heigth),
                   onClick = { onClick() },
                   shape = RoundedCornerShape(50),
                   colors = ButtonDefaults.outlinedButtonColors(
                       backgroundColor = Color.Transparent,
                       contentColor = buttonColor),
                   border = BorderStroke(width = (1.5.dp), color = buttonColor)

    ) {
        Text(text = text,
            style = textStyle
        )
    }
    
}