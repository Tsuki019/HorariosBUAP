package com.example.horariosbuap.ui.theme.customStuff.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.horariosbuap.ui.theme.backgroundColorCustom
import com.example.horariosbuap.ui.theme.customStuff.BottomNavScreens
import com.example.horariosbuap.ui.theme.customStuff.components.LoadingIndicator
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.example.horariosbuap.ui.theme.dataBase.getNoticias
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.ui.theme.dark_backgroundColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NoticiasScreen(
    navController: NavController,
    navigateToArticle: (String) -> Unit,
    datosViewModel: DatosViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    //val currentScreen = remember{ mutableStateOf<BottomNavScreens>(BottomNavScreens.Noticias)}
    val context = LocalContext.current
    val imagenView = remember { mutableStateOf(false) }
    val selectedImage = remember { mutableStateOf(R.drawable.profesional_semestral2022)}


    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .widthIn(max = 840.dp)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
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
                    datosViewModel = datosViewModel,
                    selectedImage = selectedImage,
                    imagenView = imagenView
        )
        if (imagenView.value){
            val scale = remember { mutableStateOf(1f)}
            val rotate = remember { mutableStateOf(0f)}
            val dragX = remember { mutableStateOf( 0f)}
            val dragY = remember { mutableStateOf( 0f)}

            Box(modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .clip(CircleShape.copy(all = CornerSize(8)))
                .fillMaxSize()
            ){
                Row(modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape)
                    .pointerInput(Unit) {
                        forEachGesture {
                            awaitPointerEventScope {
                                awaitFirstDown()
                                do {
                                    val event = awaitPointerEvent()
                                    scale.value *= event.calculateZoom()
                                    val offset = event.calculatePan()
                                    dragX.value += offset.x
                                    dragY.value += offset.y
                                    rotate.value += event.calculateRotation()
                                } while (imagenView.value)

                            }
                        }
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(3.dp)
                            .graphicsLayer(
                                scaleX = maxOf(.5f, minOf(3f, scale.value)),
                                scaleY = maxOf(.5f, minOf(3f, scale.value)),
                                rotationZ = rotate.value,
                                translationX = dragX.value,
                                translationY = dragY.value
                            ),
                        painter = painterResource(id = selectedImage.value),
                        contentDescription = null,
                        contentScale = ContentScale.Inside
                    )
                }
                Box(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),contentAlignment = TopEnd) {
                    IconButton(onClick = { imagenView.value = !imagenView.value }) {
                        Icon(
                            modifier = Modifier
                                //.scale(2f)
                                .height(50.dp)
                                .width(50.dp),
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun NewsContent(
    modifier: Modifier,
    navController: NavController,
    navigateToArticle : (String) -> Unit,
    datosViewModel: DatosViewModel,
    selectedImage: MutableState<Int>,
    imagenView: MutableState<Boolean>
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
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary)
        }
        item { Calendars(image = R.drawable.profesional_semestral2022, description = "Semestre 2022", selectedImage = selectedImage, imagenView = imagenView)}
        item { Calendars(image = R.drawable.profesional_cuatrimestral2022, description = "Cuatrimestre 2022", selectedImage = selectedImage, imagenView = imagenView) }
        item { Calendars(image = R.drawable.posgrado2022, description = "Posgrado 2022", selectedImage = selectedImage, imagenView = imagenView) }
        //item { Calendars(image = painterResource(id = R.drawable.semestre_2021), description = "Semestre 2021") }
        //item { Calendars(image = painterResource(id = R.drawable.cuatrimestre_2021), description = "Cuatrimestre 2021") }
        //item { Calendars(image = painterResource(id = R.drawable.posgrado_2021), description = "CPosgrado 2021") }
        item { Divider(modifier = Modifier.padding(vertical = 30.dp), color = Color.Transparent) }
    }
}

@Composable
private fun PostListDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface
) {
    Divider(
        modifier = modifier.padding(horizontal = 14.dp),
        color = color,
        thickness = 1.dp
    )
}

@ExperimentalMaterialApi
@Composable
fun NewsCard(
    news: News,
    modifier: Modifier = Modifier,
    navigateToArticle: (String) -> Unit,
    idNoticia : String
) {
    Card(
        shape =  RoundedCornerShape(
            bottomEndPercent = 8,
            bottomStartPercent = 8,
            topEndPercent = 8,
            topStartPercent = 8
        ),
        modifier = modifier.size(280.dp, 240.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.onSurface),
        onClick = { navigateToArticle(idNoticia) }
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
                            listOf(Color.Transparent, MaterialTheme.colors.background),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY * 0.4f
                        )
                    )
            )
            Column(modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)) {
                Text(
                    text = news.titulo,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
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
                        color = MaterialTheme.colors.primary,
                        fontFamily = sansPro,
                        fontSize = 15.sp
                    ),
                )
            }
        }
    }
}

@ExperimentalMaterialApi
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
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )

            LazyRow(modifier = Modifier.padding(horizontal = 3.dp)) {
                items(newsItems){ newsItem ->
                    NewsCard(
                        newsItem!!,
                        Modifier
                            .padding(start = 16.dp, bottom = 16.dp),
                        navigateToArticle = navigateToArticle,
                        idNoticia = newsItem.id
                    )
                }
            }
            PostListDivider()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun Calendars(image : Int, description : String, selectedImage: MutableState<Int>, imagenView: MutableState<Boolean>) {

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
                .fillMaxWidth(),
            shape =  RoundedCornerShape(
                bottomEndPercent = 8,
                bottomStartPercent = 8,
                topEndPercent = 8,
                topStartPercent = 8
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
            elevation = 6.dp,
            onClick = {expanded.value = !expanded.value},
            backgroundColor = MaterialTheme.colors.surface
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                    text = description,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontFamily = sansPro,
                        fontSize = 18.sp
                    )
                )
                PostListDivider(color = MaterialTheme.colors.primary)
                AnimatedVisibility(visible = expanded.value) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier
                                .padding(3.dp)
                                .clickable {
                                    selectedImage.value = image
                                    imagenView.value = !imagenView.value
                                },
                            painter = painterResource(id = image),
                            contentDescription = null,
                            contentScale = ContentScale.Inside
                        )
                        Text(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                            text = "Click en la imagen para ampliar",
                            style = TextStyle(
                                color = MaterialTheme.colors.primary,
                                fontFamily = sansPro,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }
                if (expanded.value){
                    Icon(
                        imageVector = Icons.Rounded.ExpandLess, contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }else{
                    Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
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
