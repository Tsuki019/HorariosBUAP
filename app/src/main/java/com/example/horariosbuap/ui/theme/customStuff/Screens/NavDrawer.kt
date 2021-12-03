package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.navDrawerOptions

@Composable
fun NavDrawer(
              closeDrawer: () -> Unit,
              currentRoute : String,
              navController: NavHostController,
              avatar: Painter,
              email: String) {

    val items = navDrawerOptions.Items.list

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.azulOscuroInstitucional))) {
        Spacer(modifier = Modifier.height(20.dp))
//Configuracion de la parte principal que contiene la imagen y el correo del usuario
        DrawerHead(email = email, avatar = avatar)
        Divider(modifier = Modifier.height(2.dp), color = Color.White)
//Configuracion de las opciones que aparecen en el DrawerContent
        items.forEach {
            DrawerButton(icon = it.icon,
                         label = it.opcion,
                         isSelected = currentRoute == it.destination,
                         action = {
                             closeDrawer()
                             navController.navigate(route = it.destination){
                                 popUpTo(MainDestinations.NEWS_ROUTE)
                             }
                         })
            Divider(color= Color.Transparent)
        }
    }
}

@Composable
fun DrawerHead(email: String, avatar: Painter) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
        .padding(horizontal = 8.dp, vertical = 8.dp), //ESTE NO
         verticalAlignment = Alignment.Bottom,
         horizontalArrangement = Arrangement.Start){
        Image(modifier= Modifier
            .width(80.dp)
            .height(80.dp)
            .clip(shape = CircleShape),
              painter = avatar,
              contentDescription = "",
              contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            textAlign= TextAlign.Right,
            text = email,
            fontFamily = FontFamily(Font(R.font.source_sans_pro)),
            fontSize = 15.sp,
            color = Color.White)

    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        1f
    }
    val textIconColor = if (isSelected) {
        colorResource(id = R.color.azulClaroInstitucional)
    } else {
        Color.White
    }
    val backgroundColor = if (isSelected) {
        colorResource(id = R.color.azulClaroInstitucional).copy(alpha = 0.32f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null, // decorative
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun prev1() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE

    NavDrawer(
        closeDrawer = {},
        currentRoute = currentRoute,
        navController = navController,
        avatar = painterResource(id = R.drawable.hatsune_test),
        email = "mi_emailTest@testmail.com"
    )
}