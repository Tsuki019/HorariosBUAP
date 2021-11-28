package com.example.horariosbuap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


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

