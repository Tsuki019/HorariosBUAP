package com.example.horariosbuap

import android.app.Activity
import android.icu.text.CaseMap
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.relocationRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.IconCompat
import com.example.horariosbuap.ui.theme.customStuff.CustomBottomNav
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.NavDrawer
import com.example.horariosbuap.ui.theme.customStuff.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentScreen = mutableStateOf<Screen>(Screen.Noticias)
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()

            HorariosBUAPTheme() {
                Scaffold(
                    scaffoldState= scaffoldState,
                    bottomBar = {
                        CustomBottomNav(currentScreenId = currentScreen.value.id) {
                            currentScreen.value = it
                        }
                    },
                    topBar = {CustomToolBar(title = "Horarios BUAP", scaffoldState, scope)},
                    drawerContent = { NavDrawer(scope = scope, scaffoldState = scaffoldState, email = "mi_emailTest@gmail.com")}
                ) {}
            }
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

