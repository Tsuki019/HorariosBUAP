package com.prvt.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prvt.horariosbuap.R

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color : Color = colorResource(id = R.color.azulClaroInstitucional),
    size : Dp = 80.dp
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .padding(15.dp),
            color = color
        )
    }
}