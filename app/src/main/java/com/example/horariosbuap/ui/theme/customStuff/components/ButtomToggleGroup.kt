package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.light_blue1
import com.example.horariosbuap.ui.theme.light_blue2

@Composable
fun ButtonToggleGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 12.sp
) {

    Row(modifier = modifier) {
        options.forEachIndexed { index, option ->
            val selected = selectedOption == option

            val border =
                    if (selected) BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colors.primary
                    ) else BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.primaryVariant
                    )


            val shape = when (index) {
                0 -> RoundedCornerShape(
                    topStart = 4.dp,
                    bottomStart = 4.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
                options.size - 1 -> RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp
                )
                else -> CutCornerShape(0)
            }

            val zIndex = if (selected) 1f else 0f

            val buttonModifier = when (index) {
                0 -> Modifier.zIndex(zIndex)
                else -> {
                    val offset = -1 * index
                    Modifier
                        .offset(x = offset.dp)
                        .zIndex(zIndex)
                }
            }

            val colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = if (selected) MaterialTheme.colors.onPrimary.copy(alpha = 0.12f)
                else MaterialTheme.colors.onBackground,
                contentColor = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
            )
            OutlinedButton(
                onClick = { onOptionSelect(option) },
                border = border,
                shape = shape,
                colors = colors,
                modifier = buttonModifier.weight(1f),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = option,
                    fontSize = textSize
                )
            }
        }
    }
}