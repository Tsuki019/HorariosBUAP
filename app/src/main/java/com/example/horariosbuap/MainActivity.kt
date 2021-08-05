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
import androidx.core.view.WindowCompat
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
//            val scaffoldState: ScaffoldState = rememberScaffoldState()
//            val scope = rememberCoroutineScope()
//            val navController = rememberNavController()
//            val titulos = remember { mutableStateOf("Profile") }

            HorariosAppCont()
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

