package com.prvt.horariosbuap.ui.theme.customStuff.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BackArrowButtonn(
    modifier : Modifier = Modifier,
    onBack : () -> Unit,
    tint : Color = MaterialTheme.colors.primary
) {
    IconButton(
        modifier = modifier,
        onClick = { onBack() }
    ) {
        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = tint)
    }
}