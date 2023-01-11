package com.prvt.horariosbuap.model

import androidx.compose.runtime.Composable

class News (
    var id : String = "",
    var imagen : String = "",
    var titulo : String = "",
    var fecha : String = "",
    var cuerpo : String = "",
    var link : String = "",
    var link2 : String = ""
    )
{}

@Composable
fun loadNews() : MutableList<News> {

    val list : MutableList<News> = mutableListOf()


//    for (i in 0 .. 5){
//        list.add(News(id = i.toString(), Image = painterResource(id = R.drawable.escudo_facultad), title = "Noticia $i", date = "${i*2}/${i*4}/2021"))
//    }

    return list
}