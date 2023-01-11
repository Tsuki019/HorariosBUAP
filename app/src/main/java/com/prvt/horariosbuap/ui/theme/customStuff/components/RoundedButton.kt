package com.prvt.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro

@Composable
fun RoundedButton(
    color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSurface),
    modifier: Modifier = Modifier,
    text: String,
    width : Dp = 280.dp,
    height : Dp = 50.dp,
    fontSize : TextUnit = 20.sp,
    displayProgressBar : Boolean = false,
    progressIndicatorColor : Color = MaterialTheme.colors.secondary,
    onClick: () -> Unit
) {

    if (!displayProgressBar){
        Button(
            modifier = modifier
                .width(width)
                .height(height),
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = color
        ) {
           Text(
               text = text,
               color = Color.White,
               fontSize = fontSize,
               fontFamily = sansPro,
               fontWeight = FontWeight.Medium,
               letterSpacing = 0.15.sp
           )
        }
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = progressIndicatorColor,
            strokeWidth = 6.dp
        )
    }
}