package com.example.horariosbuap

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.ui.theme.customStuff.CustomBottomNav
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.Screens.NavDrawer
import com.example.horariosbuap.ui.theme.customStuff.Screens.ui.theme.HorariosBUAPTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun HorariosAppCont (){

    HorariosBUAPTheme {
        ProvideWindowInsets {

            val systemUiController = rememberSystemUiController()
            val navBarColor = colorResource(id = R.color.azulOscuroInstitucional)
            SideEffect {
                systemUiController.setNavigationBarColor(navBarColor, darkIcons = false)
            }

            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val titulos = remember{ mutableStateOf("Empty")}
            val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }
            val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE

            Scaffold (
                scaffoldState =scaffoldState,
                topBar = {CustomToolBar(title = titulos.value, scaffoldState = scaffoldState, icon = {
                    IconButton(onClick = { coroutineScope.launch { openDrawer() } }) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "")
                    }
                })},
                drawerContent = {
                    NavDrawer(
                        closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                        currentRoute = currentRoute,
                        navController = navController,
                        avatar = painterResource(id = R.drawable.hatsune_test),
                        email = "mi_emailTest@testmail.com"
                    )
                },
            bottomBar = {
                when(titulos.value){
                    "Mi cuenta", "Ajustes", "Acerca de la Aplicacion", "Noticias"->{}
                    else->{
                        CustomBottomNav(
                            navController = navController,
                            currentRoute = currentRoute
                        )
                    }
                }
            }) {
                HorariosBuapGraph(navController = navController, scaffoldState = scaffoldState, titulos = titulos)
            }
        }

    }
}