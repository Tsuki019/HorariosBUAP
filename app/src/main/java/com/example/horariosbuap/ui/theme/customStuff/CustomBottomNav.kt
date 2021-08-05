package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.R
import com.example.horariosbuap.ui.theme.customStuff.Screems.*


@Composable
fun CustomBottomNav(
    navController: NavController,
    currentRoute: String
)
{
    val items = Screen.Items.list

    Row (
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        items.forEach{ item->
            CustomBottomNavItem(item = item, isSelected = item.destination ==  currentRoute) {
                navController.navigate(item.id)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomBottomNavItem(item:Screen,isSelected:Boolean, onClick:()->Unit) {
    val azulOscuro = colorResource(id = R.color.azulOscuroInstitucional)
    val azulClaro = colorResource(id = R.color.azulClaroInstitucional)

    val background = if (isSelected) azulOscuro
    else Color.Transparent
    val contentColor = if (isSelected) azulClaro
    else azulOscuro


    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(background)
                .clickable(onClick = onClick))
        {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Icon(imageVector = item.image,
                    contentDescription = null,
                    tint = contentColor
                )

                AnimatedVisibility(visible = isSelected) {
                    Text(text = item.title, fontFamily= FontFamily(Font(R.font.source_sans_pro)),
                    color = contentColor)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreV1() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE
    CustomBottomNav(navController = navController, currentRoute = currentRoute)
}

@Composable
@Preview
fun PreV3() {
    val onClick ={}
    CustomBottomNavItem(item = Screen.Horario, isSelected = true, onClick = onClick)
}

@Composable
@Preview
fun PreV2() {
    val onClick = {}
    CustomBottomNavItem(item = Screen.Horario, isSelected = false, onClick = onClick)
}