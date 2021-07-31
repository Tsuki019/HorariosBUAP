package com.example.horariosbuap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AssignmentReturn
import androidx.compose.material.icons.rounded.Menu
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.ui.theme.customStuff.*
import com.example.horariosbuap.ui.theme.customStuff.Screems.*
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentScreen = mutableStateOf<Screen>(Screen.Noticias)
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            val titulos = remember{ mutableStateOf("Profile")}

            HorariosBUAPTheme() {
                when(currentRoute(navController)){

                    "acerca","ajuste","cuenta"->{
                        Scaffold(
                            topBar = { CustomToolBar(icon= Icons.Rounded.ArrowBack, title = titulos.value, scaffoldState, scope){navController.navigate("noticias")} }
                        ) {

                        }
                    }
                    else->{
                        Scaffold(

                            scaffoldState = scaffoldState,
                            bottomBar = { CustomBottomNav(navController = navController, currentScreenId = currentScreen.value.id)
                            { currentScreen.value = it }
                            },
                            topBar = { CustomToolBar(icon= Icons.Rounded.Menu, title = titulos.value, scaffoldState, scope){scope.launch { scaffoldState.drawerState.open()} }},
                            drawerContent = { NavDrawer(navController = navController, scope = scope, scaffoldState = scaffoldState, email = "mi_emailTest@gmail.com")
                            }
                        ) {

                            ScreenController(navController = navController, topTitleBar = titulos)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    println("==========${navBackStackEntry?.destination?.route}")
    return navBackStackEntry?.destination?.route
}

@Composable
fun ScreenController(navController: NavHostController, topTitleBar: MutableState<String>) {


    NavHost(navController = navController,
            startDestination = "noticias")
    {
        composable("noticias"){
            NoticiasScreen()
            topTitleBar.value = "Noticias"
        }
        composable("buscar"){
            BuscarScreen()
            topTitleBar.value = "Buscar"
        }
        composable("horario"){
            HorarioScreen()
            topTitleBar.value = "Mis Horarios"
        }
        composable("libre"){
            LibreScreen()
            topTitleBar.value = "Libre"
        }
        composable("cuenta"){
            MiCuentaOption()
            topTitleBar.value = "Mi cuenta"
        }
        composable("ajuste"){
            AjustesOption()
            topTitleBar.value = "Ajustes"
        }
        composable("acerca"){
            AcercaDeOption()
            topTitleBar.value = "Acerca de la Aplicacion"
        }
        composable("compartir"){
            CompartirOption()
        }
        composable("salir"){
            SalirOption()
        }
    }
}


//val currentScreen = mutableStateOf<Screen>(Screen.Noticias)

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    HorariosBUAPTheme() {
//        Scaffold(
//            bottomBar = {
//                CustomBottomNav(currentScreenId = currentScreen.value.id) {
//                    currentScreen.value = it
//                }
//            },
//            topBar = {CustomToolBar(title = "Horarios BUAP")}
//        ) {}
//    }
//}

