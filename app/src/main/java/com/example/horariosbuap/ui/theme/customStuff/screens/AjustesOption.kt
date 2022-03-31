package com.example.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.viewmodel.UserDataViewModel

@Composable
fun AjustesOption(
    userDataViewModel: UserDataViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        contentAlignment = Alignment.TopCenter,
    ){
        Button(
            modifier = Modifier.padding(vertical = 10.dp),
            onClick = {
                userDataViewModel.userData.value =
                    userDataViewModel.userData.value.copy(
                        darkTheme = !userDataViewModel.userData.value.darkTheme)
            }
        )
        {
            Text(text = "CAMBIAR DE TEMA")
        }
    }
}