package com.example.horariosbuap.ui.theme.customStuff.components

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.viewmodel.DatosViewModel

@Composable
fun SalirAlerta(
    datosViewModel: DatosViewModel
) {

    val activity = LocalContext.current as? Activity
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(8)),
        backgroundColor = MaterialTheme.colors.background,
        onDismissRequest = { },
        title = { Text(
            text = "¿Seguro que desea salir de la aplicación?",
            color = MaterialTheme.colors.primary,
            fontSize = 22.sp,
            fontFamily = sansPro,
            textAlign = TextAlign.Justify
        ) },
        text = {},
        confirmButton = {
            TextButton(onClick = { activity?.finish() }) {
                Text(
                    text = "Salir",
                    color = MaterialTheme.colors.error,
                    fontFamily = sansPro,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { datosViewModel.salirAplicacion.value = false  }) {
                Text(
                    text = "Cancelar",
                    color = MaterialTheme.colors.primary,
                    fontFamily = sansPro,
                    fontSize = 16.sp
                )
            }
        }
    )
}