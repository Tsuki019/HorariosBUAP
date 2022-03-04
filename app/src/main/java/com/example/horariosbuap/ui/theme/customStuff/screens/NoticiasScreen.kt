package com.example.horariosbuap.ui.theme.customStuff.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.horariosbuap.R
import com.example.horariosbuap.model.News
import com.example.horariosbuap.model.loadNews
import com.example.horariosbuap.ui.theme.customStuff.BottomNavScreens
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.getNoticias
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NoticiasScreen(
    navController: NavController,
    navigateToArticle: (String) -> Unit,
    datosViewModel: DatosViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val currentScreen = remember{ mutableStateOf<BottomNavScreens>(BottomNavScreens.Noticias)}
    val context = LocalContext.current

    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .widthIn(max = 840.dp)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blanco_fondo))
    ) {
        if (!datosViewModel.isNewsFill.value){
            coroutineScope.launch {
                getNoticias(datosViewModel = datosViewModel, context)
                println("CARGA NOTICIAS")
            }
        }
        NewsContent(modifier = modifier,
                    navController = navController,
                    navigateToArticle = navigateToArticle,
                    datosViewModel = datosViewModel
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun NewsContent(
    modifier: Modifier,
    navController: NavController,
    navigateToArticle : (String) -> Unit,
    datosViewModel: DatosViewModel
){
    LazyColumn(
        modifier = modifier,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = false
        )
    ) {
        item { Noticias(datosViewModel, navigateToArticle) }
        item { Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = "Calendarios",
            style = TextStyle(
                color= colorResource(id = R.color.azulOscuroInstitucional),
                fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                fontWeight = FontWeight.Bold),
                fontSize = 20.sp
            )
        }
        item { Calendars(image = painterResource(id = R.drawable.profesional_semestral2022), description = "Semestre 2022")}
        item { Calendars(image = painterResource(id = R.drawable.profesional_cuatrimestral2022), description = "Cuatrimestre 2022") }
        item { Calendars(image = painterResource(id = R.drawable.posgrado2022), description = "Posgrado 2022") }
        //item { Calendars(image = painterResource(id = R.drawable.semestre_2021), description = "Semestre 2021") }
        //item { Calendars(image = painterResource(id = R.drawable.cuatrimestre_2021), description = "Cuatrimestre 2021") }
        //item { Calendars(image = painterResource(id = R.drawable.posgrado_2021), description = "CPosgrado 2021") }
        item { Divider(modifier = Modifier.padding(vertical = 30.dp), color = Color.Transparent) }
    }
}

@Composable
private fun PostListDivider(
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier.padding(horizontal = 14.dp),
        color = colorResource(id = R.color.azulOscuroInstitucional),
        thickness = 1.dp
    )
}

@Composable
fun NewsCard(
    news: News,
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
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.azulOscuroInstitucional))
    ) {
        Box(contentAlignment = BottomStart) {

            Image(
                painter = rememberImagePainter(data = news.imagen),
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, Color.White),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY * 0.4f
                        )
                    )
            )
            Column(modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)) {
                Text(
                    text = news.titulo,
                    style = TextStyle(
                        color = primaryColorCustom,
                        fontFamily = sansPro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = news.fecha,
                    style = TextStyle(
                        color = primaryColorCustom,
                        fontFamily = sansPro,
                        fontSize = 15.sp
                    ),
                )
            }
        }
    }
}

@Composable
private fun Noticias(
    datosViewModel: DatosViewModel,
    navigateToArticle: (String) -> Unit)
{
    if (!datosViewModel.isNewsFill.value){
        Box(contentAlignment = Center)
        {
            LoadingIndicator()
        }
    }else{
        val newsItems = datosViewModel.noticias
        Column() {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Últimas Noticias",
                style = MaterialTheme.typography.h5.copy(
                    color = colorResource(id = R.color.azulOscuroInstitucional),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro))))

            LazyRow(modifier = Modifier.padding(horizontal = 3.dp)) {
                items(newsItems){ newsItem ->
                    NewsCard(
                        newsItem!!,
                        Modifier
                            .padding(start = 16.dp, bottom = 16.dp)
                            .clickable { navigateToArticle(newsItem.id) }
                    )
                }
            }
            PostListDivider()
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun Calendars(image : Painter, description : String) {

    val expanded = remember {mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 6.dp)
                .padding(horizontal = 15.dp)
                .align(alignment = CenterHorizontally)
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value },
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
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                    text = description,
                    style = TextStyle(
                        color = colorResource(id = R.color.azulOscuroInstitucional),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                        fontSize = 18.sp
                    )
                )
                PostListDivider()
                AnimatedVisibility(visible = expanded.value) {
                    Image(
                        modifier = Modifier.padding(vertical = 3.dp),
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
                if (expanded.value){
                    Icon(imageVector = Icons.Rounded.ExpandLess, contentDescription = "")
                }else{
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "")
                }
            }
        }

    }
}



//@ExperimentalAnimationApi
//@Preview
//@Composable
//fun NotiView() {
//    val navController = rememberNavController()
//    val scaffoldState = rememberScaffoldState()
//
//    NoticiasScreen(navController = navController,
//                   navigateToArticle = {  })
//}
