package com.prvt.horariosbuap.ui.theme.customStuff

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prvt.horariosbuap.ui.theme.dark_blue2
import com.prvt.horariosbuap.ui.theme.light_blue1

@Composable
fun CustomToolBar(
backgroundColor: Color = dark_blue2,
elevation: Dp = 6.dp,
modifier: Modifier = Modifier,
title: String,
scaffoldState: ScaffoldState,
icon: @Composable (() -> Unit)? = null)
{

    Surface(
            elevation = elevation,
            modifier = modifier,
    ) {
        TopAppBar(
            title = {
                Row(modifier= Modifier
                    .fillMaxSize()
                    .padding(end = 60.dp)
                    ,verticalAlignment = Alignment.CenterVertically ) {
                    Text(modifier= Modifier.fillMaxWidth(),
                         text = title,
                         fontFamily = sansPro,
                         textAlign= TextAlign.Center
                    )
                }
            },
            navigationIcon = icon,
            backgroundColor = backgroundColor,
            contentColor = light_blue1,
            elevation = 12.dp,
        )

    }
}


//@Preview
//@Composable
//fun PrevToolBar() {
//    CustomToolBar(title = "Horarios BUAP", )
//}

