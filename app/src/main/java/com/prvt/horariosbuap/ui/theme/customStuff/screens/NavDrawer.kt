package com.prvt.horariosbuap.ui.theme.customStuff.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.prvt.horariosbuap.MainDestinations
import com.prvt.horariosbuap.R
import com.prvt.horariosbuap.ui.theme.customStuff.navDrawerOptions
import com.prvt.horariosbuap.viewmodel.DatosViewModel
import com.prvt.horariosbuap.viewmodel.UserDataViewModel

@Composable
fun NavDrawer(
    closeDrawer: () -> Unit,
    currentRoute : String,
    navController: NavHostController,
    avatar: Painter,
    email: String,
    userDataViewModel : UserDataViewModel,
    datosViewModel: DatosViewModel
) {

    val items = navDrawerOptions.Items.list

    //Variables para compartir
    val context = LocalContext.current
    val sendIntent : Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "https://www.buap.mx/ Busca tus materias, tus maestros, salones y forma tus horarios de la Facultad de Ciencias de la Computación BUAP.")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.azulOscuroInstitucional))
//            .widthIn(200.dp, 400.dp)
        ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
//Configuracion de la parte principal que contiene la imagen y el correo del usuario
        item {
            DrawerHead(email = email, avatar = avatar)
        }
        item {
            Divider(modifier = Modifier.height(2.dp), color = Color.White)
        }
//Configuracion de las opciones que aparecen en el DrawerContent
        items.forEach {
            item {
                DrawerButton(icon = it.icon,
                    label = it.opcion,
                    isSelected = currentRoute == it.destination,
                    action = {
                        closeDrawer()
                        if (it.destination == "Salir") {
                            datosViewModel.salirAplicacion.value = true

                        }else if (it.destination == "Compartir"){
                            context.startActivity(shareIntent)
                        }else{
                            navController.navigate(route = it.destination){
                                popUpTo(MainDestinations.NEWS_ROUTE)
                            }
                        }
                    })

                Divider(color= Color.Transparent)
            }
        }


        //Boton temporal para el cambio de tema, para un acceso rapido en las pruebas
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 5.dp),
//            contentAlignment = Alignment.TopCenter,
//        ){
//            Button(
//                modifier = Modifier.padding(vertical = 10.dp),
//                onClick = {
//                    userDataViewModel.setTheme(!userDataViewModel.isDarkTheme.value)
//                },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MaterialTheme.colors.secondaryVariant,
//                    contentColor = MaterialTheme.colors.primary
//                )
//            )
//            {
//                Text(text = "CAMBIAR DE TEMA")
//            }
//        }
    }
}

@Composable
fun DrawerHead(
    email: String,
    avatar: Painter
) {
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
              contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            textAlign= TextAlign.Right,
            text = email,
            fontFamily = FontFamily(Font(R.font.source_sans_pro)),
            fontSize = 20.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

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

//@Preview
//@Composable
//fun prev1() {
//    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE
//
//    NavDrawer(
//        closeDrawer = {},
//        currentRoute = currentRoute,
//        navController = navController,
//        avatar = painterResource(id = R.drawable.default_image),
//        email = "mi_emailTest@testmail.com"
//    )
//}