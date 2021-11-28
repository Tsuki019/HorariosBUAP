package com.example.horariosbuap

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations.SINGLE_NEW_KEY
import com.example.horariosbuap.ui.theme.customStuff.Screens.*
import com.example.horariosbuap.ui.theme.customStuff.Screens.ui.theme.VistaNoticia
import kotlinx.coroutines.launch


object MainDestinations{

    const val NEWS_ROUTE = "noticias"
    const val SEARCH_ROUTE = "buscar"
    const val SCHEDULE_ROUTE = "horarios"
    const val FREE_ROUTE = "libre"
    const val ACCOUNT_ROUTE = "cuenta"
    const val SETTINGS_ROUTE = "ajustes"
    const val ABOUT_ROUTE = "acerca"
    const val SHARE_ROUTE = "compartir"
    const val EXIT_ROUTE = "salir"
    const val HOME_ROUTE = "inicio"
    const val SINGLE_NEW = "noticia_individual"
    const val SINGLE_NEW_KEY = "noticiaId"
    const val LOGIN_ROUTE = "ingresar"
    const val REGISTRATION_ROUTE = "registrarse"
    const val NOLOGIN_ROUTE = "no_ingresado"
}

@Composable
fun HorariosBuapGraph(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    startDestination: String = MainDestinations.NEWS_ROUTE,
    titulos: MutableState<String>
) {

    val actions  = MainActions(navController = navController)
    val coroutineScope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }


    NavHost(navController = navController,
            startDestination = startDestination,
    ){

        composable(MainDestinations.HOME_ROUTE){
            NoticiasScreen(navController = navController,
                           scaffoldState = scaffoldState,
                           navigateToArticle = actions.navigateToArticle,
                           openDrawer = openDrawer)
            titulos.value = "Noticias"
        }
        composable(MainDestinations.NEWS_ROUTE){
            NoticiasScreen(navController = navController,
                           scaffoldState = scaffoldState,
                           navigateToArticle = actions.navigateToArticle,
                           openDrawer = openDrawer)
            titulos.value = "Noticias y calendarios"
        }
        composable(MainDestinations.SEARCH_ROUTE){
            BuscarScreen(navController = navController,scaffoldState = scaffoldState, openDrawer = openDrawer)
            titulos.value = "Buscar"
        }
        composable(MainDestinations.SCHEDULE_ROUTE){
            HorarioScreen(navController = navController,scaffoldState = scaffoldState, openDrawer = openDrawer)
            titulos.value = "Mi Horario"
        }
        composable(MainDestinations.FREE_ROUTE){
            LibreScreen(navController = navController,scaffoldState = scaffoldState, openDrawer = openDrawer)
            titulos.value = "Espacio Libre"
        }
        composable(MainDestinations.ACCOUNT_ROUTE){
            MiCuentaOption(navController = navController)
            titulos.value = "Mi cuenta"
        }
        composable(MainDestinations.SETTINGS_ROUTE){
            AjustesOption()
            titulos.value = "Ajustes"
        }
        composable(MainDestinations.ABOUT_ROUTE){
            AcercaDeOption()
            titulos.value = "Acerca de la Aplicacion"
        }
        composable(MainDestinations.SHARE_ROUTE){
            CompartirOption()
        }
        composable(MainDestinations.EXIT_ROUTE){
            SalirOption()
        }
        composable("${MainDestinations.SINGLE_NEW}/{$SINGLE_NEW_KEY}"){
                backStackEntry ->
            VistaNoticia(noticiaId = backStackEntry.arguments?.getString(SINGLE_NEW_KEY))
//            titulos.value = "Noticias"
        }
        composable(MainDestinations.LOGIN_ROUTE){
            LoginScreen(navController = navController)
        }
        composable(MainDestinations.REGISTRATION_ROUTE){
            RegistrationScreen(navController = navController)
        }
        composable(MainDestinations.NOLOGIN_ROUTE){
            RegistrationScreen(navController = navController)
        }
    }
}

class MainActions(navController: NavHostController){
    val navigateToArticle:(String) ->Unit = {postId: String ->
        navController.navigate("${MainDestinations.SINGLE_NEW}/${postId}")
    }
}