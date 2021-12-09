package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.Screen
import com.example.horariosbuap.ui.theme.dataBase.LoadNews
import com.example.horariosbuap.ui.theme.dataBase.News
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun NoticiasScreen(
    navController: NavController,
    navigateToArticle: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = remember{ mutableStateOf<Screen>(Screen.Noticias)}
    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .widthIn(max = 840.dp)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blanco_fondo))
    ) {
        NewsContent(modifier = modifier,
                    navController = navController,
                    navigateToArticle = navigateToArticle)
    }
}

@Composable
private fun NewsContent(modifier: Modifier,
                        navController: NavController,
                        navigateToArticle : (String) -> Unit){
    LazyColumn(
        modifier = modifier,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = false
        )
    ) {
        item { ImageDrawer(navController, navigateToArticle) }
        item { Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = "Calendarios",
            style = MaterialTheme.typography.h6.copy(color= colorResource(id = R.color.azulOscuroInstitucional), fontWeight = FontWeight.Bold),
            ) }
        item { Calendars(image = painterResource(id = R.drawable.semestre_2021), description = "Calendario para Semestre 2020-2021") }
        item { Calendars(image = painterResource(id = R.drawable.cuatrimestre_2021), description = "Calendario para Cuatrimestre 2020-2021") }
        item { Calendars(image = painterResource(id = R.drawable.posgrado_2021), description = "Calendario para Posgrado 2020-2021") }
    }
}

@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = colorResource(id = R.color.azulOscuroInstitucional),
        thickness = 1.dp
    )
}

@Composable
fun PostCardPopular(
    news: News,
    navigateToArticle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape =  RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        ),
        modifier = modifier.size(280.dp, 240.dp),
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azulOscuroInstitucional))
    ) {
//        Column(modifier = Modifier.clickable(onClick = { navigateToArticle(post.id) })) {
        Column() {

            Image(
                painter = news.Image,
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
//                Text(
//                    text = post.metadata.author.name,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    style = MaterialTheme.typography.body2
//                )

                Text(
                    text = news.date,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
//private fun ImageDrawer(navigateToArticle: (String) -> Unit){
private fun ImageDrawer(navController: NavController,
                        navigateToArticle: (String) -> Unit){

    val newsItems = LoadNews()

    Column() {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Últimas Noticias",
            style = MaterialTheme.typography.h5.copy(
                color = colorResource(id = R.color.azulOscuroInstitucional),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.source_sans_pro))))
        
        LazyRow(modifier = Modifier.padding(end = 16.dp)) {
            items(newsItems){ newsItem ->
                PostCardPopular(
                    newsItem,
                    navigateToArticle,
                    Modifier
                        .padding(start = 16.dp, bottom = 16.dp)
                        .clickable { navigateToArticle(newsItem.id) }
                )
            }
        }
        PostListDivider()
    }
}

@Composable
private fun Calendars(image : Painter, description : String) {
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 6.dp)
                .padding(horizontal = 15.dp)
                .height(250.dp)
                .align(alignment = CenterHorizontally)
                .fillMaxWidth(),
            shape =  RoundedCornerShape(
                bottomEndPercent = 8,
                bottomStartPercent = 8,
                topEndPercent = 8,
                topStartPercent = 8
            ),
            border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.azulOscuroInstitucional)),
            elevation = 6.dp
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 3.dp),
                    text = description,
                    style = MaterialTheme.typography.subtitle1.copy(colorResource(id = R.color.azulOscuroInstitucional))
                )
                Image(
                    modifier = Modifier.padding(vertical = 3.dp),
                    painter = image,
                    contentDescription = null)
            }
        }

    }
}



@Preview
@Composable
fun NotiView() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    NoticiasScreen(navController = navController,
                   navigateToArticle = { /*TODO*/ })
}
