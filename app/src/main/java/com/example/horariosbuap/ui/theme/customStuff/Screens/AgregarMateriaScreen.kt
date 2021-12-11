package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.components.TransparentTextField
import com.example.horariosbuap.ui.theme.dataBase.MateriaTabla
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun AgregarMateriaScreen(
    modifier : Modifier
) {

    val materias : List<MateriaTabla>
    val busquedaValue : MutableState<String> = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blanco_fondo))
            .padding(horizontal = 8.dp)
    ){
        Column(modifier = modifier.fillMaxWidth()) {
            TransparentTextField(
                textFieldValue = busquedaValue,
                textLabel = "Buscar Materia",
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        //TODO (Proceso de busqueda)
                    }
                ),
                imeAction = ImeAction.Done,
                focusColor = colorResource(id = R.color.azulClaroInstitucional),
                unFocusedColor = colorResource(id = R.color.azulOscuroInstitucional),
                textColor = colorResource(id = R.color.azulOscuroInstitucional),
                maxChar = 30,
                trailingIcon = {
                    IconButton(onClick = {
                        //TODO (Proceso de busqueda)
                    })
                    {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "",
                            tint = colorResource(id = R.color.azulOscuroInstitucional)
                        )
                    }
                }
            )
            LazyColumn(modifier = modifier.fillMaxWidth(),
            contentPadding = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.systemBars, applyTop = false)
            ){
                item {  }
            }
        }
    }
}