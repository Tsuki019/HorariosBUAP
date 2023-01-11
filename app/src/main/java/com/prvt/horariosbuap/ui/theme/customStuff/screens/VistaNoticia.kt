package com.prvt.horariosbuap.ui.theme.customStuff.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.prvt.horariosbuap.model.News
import com.prvt.horariosbuap.ui.theme.customStuff.sansPro
import com.prvt.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun VistaNoticia(
    noticiaId : String?,
    datosViewModel: DatosViewModel
) {

    val newsList = datosViewModel.noticias

    val noticia = newsList.find {
        (it?.id ?: 0) == noticiaId
    }

    if (noticia != null){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
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
    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                text = "Error al cargar la noticia",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color= MaterialTheme.colors.primary,
                    fontFamily = sansPro,
                    fontWeight = FontWeight.Bold),
                fontSize = 24.sp
            )
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
                    color= MaterialTheme.colors.primary,
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
                text = noticia.cuerpo.replace("<br>", "\n"),
                textAlign = TextAlign.Justify,
                style = TextStyle(
                    fontFamily = sansPro,
                    color = MaterialTheme.colors.primary
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
                        color = MaterialTheme.colors.primary,
                        fontSize = 15.sp
                )

                val uriHandler = LocalUriHandler.current
                val linkNoticia : AnnotatedString = buildAnnotatedString {
                    append(noticia.link)
                    addStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.secondary,
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
                    overflow = TextOverflow.Ellipsis,
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
                            color = MaterialTheme.colors.secondary,
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
                    overflow = TextOverflow.Ellipsis,
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
                fontSize = 15.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
    
}

@Composable
private fun NoticiaDivider(
    color : Color = MaterialTheme.colors.primary,
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