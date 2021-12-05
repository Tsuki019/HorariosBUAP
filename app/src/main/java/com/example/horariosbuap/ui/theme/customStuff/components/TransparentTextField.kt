package com.example.horariosbuap.ui.theme.customStuff.components

import android.graphics.ColorSpace
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.horariosbuap.R

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<String>,
    textLabel: String,
    maxChar: Int? = null,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
    focusColor: Color = colorResource(id = R.color.azulClaroInstitucional),
    unFocusedColor : Color = Color.DarkGray,
    textColor : Color = Color.Black,
    trailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None  //Visualizacion de contrasenas
) {

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldValue.value.take(maxChar?: 40),
        onValueChange = {textFieldValue.value = it},
        label = { Text(text = textLabel)},
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = focusColor,
            focusedIndicatorColor = focusColor,
            focusedLabelColor = focusColor,
            unfocusedIndicatorColor = unFocusedColor,
            unfocusedLabelColor = unFocusedColor,
            textColor = textColor
        )
    )
}