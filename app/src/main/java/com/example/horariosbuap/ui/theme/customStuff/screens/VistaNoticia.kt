package com.example.horariosbuap.ui.theme.customStuff.screens.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.horariosbuap.model.News
import com.example.horariosbuap.ui.theme.backgroundColorCustom
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.ui.theme.secondaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun VistaNoticia(
    noticiaId : String?,
    datosViewModel: DatosViewModel
) {

    val newsList = datosViewModel.noticias
    var noticia  = News()

    newsList.forEach {
        if (it != null) {
            if (it.id == noticiaId){
                noticia = it
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColorCustom)
    ) {
        LazyColumn(
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = false
            )
        ) {
            item { Noticia(noticia = noticia) }
        }
    }
}

@Composable
fun Noticia( noticia : News) {
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 10.dp, top = 3.dp)
    ) {
        Column(Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = noticia.titulo,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color= primaryColorCustom,
                    fontFamily = sansPro,
                    fontWeight = FontWeight.Bold),
                fontSize = 24.sp
            )
            NoticiaDivider()
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 300.dp),
                painter = rememberImagePainter(data = noticia.imagen),
                contentDescription = "",
                contentScale = ContentScale.Inside)
            NoticiaDivider()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = noticia.cuerpo,
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    fontFamily = sansPro,
                ),
                fontSize = 18.sp
            )
            NoticiaDivider(thickness = 1.dp, verticalPadding = 7.dp)
            if (noticia.link != ""){
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 8.dp),
                    text = "Más información:",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = sansPro,
                        fontWeight = FontWeight.Bold),
                    fontSize = 15.sp
                )

                val uriHandler = LocalUriHandler.current
                val linkNoticia : AnnotatedString = buildAnnotatedString {
                    append(noticia.link)
                    addStyle(
                        style = SpanStyle(
                            color = secondaryColorCustom,
                            fontSize = 15.sp,
                            fontFamily = sansPro,
                            textDecoration = TextDecoration.Underline,
                        ),
                        start = 0,
                        end = noticia.link.lastIndex + 1
                    )
                    addStringAnnotation(
                        tag = "URL",
                        annotation = noticia.link,
                        start = 0,
                        end = noticia.link.lastIndex
                    )
                }
                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = linkNoticia,
                    maxLines = 1,
                    onClick = {
                        linkNoticia
                            .getStringAnnotations("URL", it, it)
                            .firstOrNull()?.let { stringAnnotation ->
                                uriHandler.openUri(stringAnnotation.item)
                            }
                    }
                )
            }
            if (noticia.link2 != ""){
                val uriHandler = LocalUriHandler.current
                val linkNoticia : AnnotatedString = buildAnnotatedString {
                    append(noticia.link2)
                    addStyle(
                        style = SpanStyle(
                            color = secondaryColorCustom,
                            fontSize = 15.sp,
                            fontFamily = sansPro,
                            textDecoration = TextDecoration.Underline,
                        ),
                        start = 0,
                        end = noticia.link2.lastIndex + 1
                    )
                    addStringAnnotation(
                        tag = "URL",
                        annotation = noticia.link2,
                        start = 0,
                        end = noticia.link2.lastIndex
                    )
                }
                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 10.dp),
                    text = linkNoticia,
                    maxLines = 1,
                    onClick = {
                        linkNoticia
                            .getStringAnnotations("URL", it, it)
                            .firstOrNull()?.let { stringAnnotation ->
                                uriHandler.openUri(stringAnnotation.item)
                            }
                    }
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                text = "Fecha: " + noticia.fecha,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = sansPro,
                    fontWeight = FontWeight.Bold),
                fontSize = 15.sp
            )
        }
    }
    
}

@Composable
private fun NoticiaDivider(
    color : Color = primaryColorCustom,
    thickness : Dp = 2.dp,
    verticalPadding : Dp = 6.dp
    ) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = verticalPadding),
        thickness = thickness,
        color = color
    )
}