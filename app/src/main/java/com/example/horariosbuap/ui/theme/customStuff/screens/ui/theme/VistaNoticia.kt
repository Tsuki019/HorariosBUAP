package com.example.horariosbuap.ui.theme.customStuff.screens.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.horariosbuap.R
import com.example.horariosbuap.model.News
import com.example.horariosbuap.model.loadNews

@Composable
fun VistaNoticia(noticiaId : String?) {

    val newsList = loadNews()
    var noticia  = News(id = "-1",
                        Image = painterResource(id = R.drawable.escudo_facultad),
                        title = "No se encontro la noticia",
                        date = "Sin fecha")

    //Busqueda temporal sin base de datos

    for (i in newsList.indices) {
        if (i.toString() == noticiaId) {
            noticia = newsList[i]
            break
        }
    }

    com.example.horariosbuap.ui.theme.HorariosBUAPTheme() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = noticia.title)
        }
    }
}