package com.example.horariosbuap

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme
import com.example.horariosbuap.ui.theme.customStuff.CustomBottomNav
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.Screens.*
import com.example.horariosbuap.ui.theme.dataBase.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            HorariosBUAPTheme {

                val viewModel : LoginViewModel by viewModels()

                val systemUiController = rememberSystemUiController()
                val navBarColor = colorResource(id = R.color.azulOscuroInstitucional)
                SideEffect {
                    systemUiController.setNavigationBarColor(navBarColor, darkIcons = false)
                }

                val navController = rememberAnimatedNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }
                val titulos = remember{ mutableStateOf("Empty") }

                BoxWithConstraints {

                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            CustomToolBar(
                                title = titulos.value,
                                scaffoldState = scaffoldState,
                                icon = {
                                    IconButton(onClick = { coroutineScope.launch { openDrawer() } }) {
                                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "")
                                    }
                                }
                            )
                        },
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
                                "Mi cuenta", "Ajustes", "Acerca de la Aplicacion", "Noticias", "Horarios Buap"->{}
                                else->{
                                    CustomBottomNav(
                                        navController = navController,
                                        currentRoute = currentRoute
                                    )
                                }
                            }
                        }
                    ) {
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = MainDestinations.NEWS_ROUTE,
                        ){
                            addNews(navController = navController,
                                    titulos = titulos)
                            addSinglePostView(navController = navController,
                                              titulos = titulos)
                            addSearch(navController = navController,
                                      titulos = titulos)
                            addSchedule(navController = navController,
                                        titulos = titulos)
                            addFree(navController = navController,
                                    titulos = titulos)
                            addAccountOpt(navController = navController,
                                          titulos = titulos)
                            addSettingsOpt(navController = navController,
                                           titulos = titulos)
                            addAboutOpt(navController = navController,
                                        titulos = titulos)
                            addShareOpt(navController = navController,
                                        titulos = titulos)
                            addExitOpt(navController = navController,
                                       titulos = titulos)
                            addLogin(
                                navController = navController,
                                viewModel = viewModel,
                                activity = this@MainActivity,
                                titulos = titulos
                            )
                            addRegister(navController = navController,
                                     titulos = titulos)
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        println("ENTRA A MAINACTIVITY onActivityResult")

        val viewModel : LoginViewModel by viewModels()
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.finishLogin(task)
        }else{
            viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_login_google)
        }
    }
}