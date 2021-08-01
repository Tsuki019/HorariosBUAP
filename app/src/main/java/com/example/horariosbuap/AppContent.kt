package com.example.horariosbuap

import android.icu.text.LocaleDisplayNames
import android.telephony.UiccCardInfo
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.ui.theme.customStuff.CustomBottomNav
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.Screems.NavDrawer
import com.example.horariosbuap.ui.theme.customStuff.Screems.ui.theme.HorariosBUAPTheme
import com.example.horariosbuap.ui.theme.customStuff.Screen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun HorariosAppCont (){

    HorariosBUAPTheme {
        ProvideWindowInsets() {

            val systemUiController = rememberSystemUiController()
            SideEffect {
//                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
            }

            val navController = rememberNavController()
            val coroutineScope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = remember{ mutableStateOf<Screen>(Screen.Noticias) }
            val titulos = remember{ mutableStateOf("Profile")}
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
                        navController = navController,
                        scope = coroutineScope,
                        scaffoldState = scaffoldState,
                        avatar = painterResource(id = R.drawable.hatsune_test),
                        email = "mi_emailTest@test.com"
                    )
                },
            bottomBar = {
                when(titulos.value){
                    "Mi cuenta", "Ajustes", "Acerca de la Aplicacion"->{}
                    else->{
                        CustomBottomNav(
                            navController = navController,
                            currentScreenId = currentScreen.value.id,
                            onItemSelected = {currentScreen.value = it}
                        )
                    }
                }
            }) {
                HorariosBuapGraph(navController = navController, scaffoldState = scaffoldState, titulos = titulos)
            }
        }

    }
}