package com.example.horariosbuap.ui.theme.dataBase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.horariosbuap.R
import org.w3c.dom.Text

class News (
    var id : String,
    var Image : Painter,
    var title : String,
    var date : String
    )
{}

@Composable
fun loadNews() : MutableList<News> {
    val list : MutableList<News> = mutableListOf()

    for (i in 0 .. 5){
        list.add(News(id = i.toString(), Image = painterResource(id = R.drawable.escudo_facultad), title = "Noticia $i", date = "${i*2}/${i*4}/2021"))
    }

    return list
}