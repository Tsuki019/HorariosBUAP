package com.example.horariosbuap

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.MainDestinations.SINGLE_NEW_KEY
import com.example.horariosbuap.ui.theme.customStuff.Screens.*
import com.example.horariosbuap.ui.theme.customStuff.Screens.ui.theme.VistaNoticia
import com.example.horariosbuap.ui.theme.dataBase.*
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.composable


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
    const val SINGLE_NEW = "noticia_individual"
    const val SINGLE_NEW_KEY = "noticiaId"
    const val LOGIN_ROUTE = "ingresar"
    const val REGISTRATION_ROUTE = "registrarse"
    const val SEARCH_RESULT_ROUTE = "resultados_busqueda"
    const val ADD_SUBJECT_ROUTE = "agregar_materia"
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addNews(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.NEWS_ROUTE,
               enterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               }
    ){

        val actions  = MainActions(navController = navController)

        NoticiasScreen(
            navController = navController,
            navigateToArticle = actions.navigateToArticle,
        )
        titulos.value = "Noticias y Calendarios"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSinglePostView(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = "${MainDestinations.SINGLE_NEW}/{$SINGLE_NEW_KEY}",
               enterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               }
    ){ backStackEntry ->
            VistaNoticia(noticiaId = backStackEntry.arguments?.getString(SINGLE_NEW_KEY))
            titulos.value = "Noticias"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSearch(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.SEARCH_ROUTE,
               enterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               }
    ){
        val profeOpc = 1
        BuscarScreen(
            onSearchProfesor = {
//                navController.navigate(route = MainDestinations.SEARCH_RESULT_ROUTE+"/$profeOpc"){
                navController.navigate(route = MainDestinations.SEARCH_RESULT_ROUTE){
                    popUpTo(MainDestinations.SEARCH_ROUTE){inclusive =true}
                }
            },
            onSearchSalon = {},
            onSearchMateria = {},
        )
        titulos.value = "Buscar"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSchedule(
    navController: NavHostController,
    viewModel: LoginViewModel,
    titulos: MutableState<String>,
    datosViewModel: DatosViewModel
){
    composable(route = MainDestinations.SCHEDULE_ROUTE,
               enterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               }
    ){
        HorarioScreen(
            navController = navController,
            viewModel = viewModel,
            datosViewModel = datosViewModel,
            onAddSubject = {
                navController.navigate(route = MainDestinations.ADD_SUBJECT_ROUTE)
            })
        titulos.value = "Mi horario"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addFree(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.FREE_ROUTE,
               enterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   slideInVertically(
                       initialOffsetY = {-2000},
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   slideOutVertically(
                       targetOffsetY = {2000},
                       animationSpec = tween(500)
                   )
               }
    ){
        LibreScreen(navController = navController)
        titulos.value = "Espacio Libre"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAccountOpt(
    navController: NavHostController,
    viewModel: LoginViewModel,
    titulos: MutableState<String>,
    activity: Activity
){
    composable(route = MainDestinations.ACCOUNT_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){
        MiCuentaOption(
            viewModel = viewModel,
            activity = activity,
            onSignOut = {navController.navigate(route= MainDestinations.LOGIN_ROUTE) {
                popUpTo(MainDestinations.ACCOUNT_ROUTE){
                    inclusive = true } } }
        )

        titulos.value = "Mi cuenta"

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSettingsOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.SETTINGS_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){
        AjustesOption()
        titulos.value = "Ajustes"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAboutOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.ABOUT_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){
        AcercaDeOption()
        titulos.value = "Acerca de la Aplicacion"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addShareOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.SHARE_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){
        CompartirOption()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addExitOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.EXIT_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){
        SalirOption()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addLogin(
    navController: NavHostController,
    viewModel : LoginViewModel,
    registerViewModel: RegisterViewModel,
    activity: Activity,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.LOGIN_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){

        if (viewModel.state.value.successLogin){
            LaunchedEffect(key1 = Unit){
                navController.navigate(route = MainDestinations.ACCOUNT_ROUTE){
                    popUpTo(MainDestinations.LOGIN_ROUTE){
                        inclusive = true
                    }
                }
            }
        }else{
            LoginScreen(state = viewModel.state.value,
                        registerViewModel = registerViewModel,
                        onLogin = viewModel::login,
                        onLoginWithGoogle = viewModel::loginWithGoogle,
                        activity = activity,
                        onDissmisDialog = viewModel::hideErrorDialog,
                        onNavigateToRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)}
            )
            titulos.value = "Horarios Buap"
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addRegister(
    activity: Activity,
    viewModelLogin : LoginViewModel,
    viewModel : RegisterViewModel,
    navController: NavHostController,
    titulos: MutableState<String>,
){
    composable(route = MainDestinations.REGISTRATION_ROUTE,
               enterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               exitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popEnterTransition = {_, _ ->
                   fadeIn(
                       initialAlpha = 0F,
                       animationSpec = tween(500)
                   )
               },
               popExitTransition = {_, _ ->
                   fadeOut(
                       targetAlpha = 0F,
                       animationSpec = tween(500)
                   )
               }
    ){

        if(!viewModel.state.value.successRegister){
            RegistrationScreen(
                state = viewModel.state.value,
                onRegister = viewModel::register,
                onBack = { navController.navigate(route = MainDestinations.LOGIN_ROUTE) },
                onLoginWithGoogle = viewModelLogin::loginWithGoogle,
                activity = activity,
                onDismissDialog = viewModel::hideErrorDialog,
                loginViewModel = viewModelLogin
            )
            titulos.value = "Horarios Buap"
        }else{
            LoginScreen(state = viewModelLogin.state.value,
                        registerViewModel = viewModel,
                        onLogin = viewModelLogin::login,
                        onLoginWithGoogle = viewModelLogin::loginWithGoogle,
                        activity = activity,
                        onDissmisDialog = viewModelLogin::hideErrorDialog,
                        onNavigateToRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)}
            )
            titulos.value = "Horarios Buap"
        }



//        if (viewModelLogin.state.value.successLogin){
//            LaunchedEffect(key1 = Unit){
//                navController.navigate(route = MainDestinations.ACCOUNT_ROUTE){
//                    popUpTo(MainDestinations.LOGIN_ROUTE){
//                        inclusive = true
//                    }
//                }
//            }
//        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAddSubject(
    navController: NavHostController,
) {
    composable(route = MainDestinations.ADD_SUBJECT_ROUTE, enterTransition = { _, _ ->
        fadeIn(
            initialAlpha = 0F, animationSpec = tween(500)
        )
    }, exitTransition = { _, _ ->
        fadeOut(
            targetAlpha = 0F, animationSpec = tween(500)
        )
    }, popEnterTransition = { _, _ ->
        fadeIn(
            initialAlpha = 0F, animationSpec = tween(500)
        )
    }, popExitTransition = { _, _ ->
        fadeOut(
            targetAlpha = 0F, animationSpec = tween(500)
        )
    }) {
        val modifier =  Modifier
        AgregarMateriaScreen(modifier = modifier)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSearchResultScreen(
    datosViewModel: DatosViewModel
) {

    composable(
//        route = MainDestinations.SEARCH_RESULT_ROUTE + "/{tipo}",
        route = MainDestinations.SEARCH_RESULT_ROUTE,

//        arguments = listOf(
//            navArgument("tipo"){type = NavType.IntType}
//        ),
        enterTransition = { _, _ ->
            fadeIn(
                initialAlpha = 0F, animationSpec = tween(500)
            )
        }, exitTransition = { _, _ ->
            fadeOut(
                targetAlpha = 0F, animationSpec = tween(500)
            )
        }, popEnterTransition = { _, _ ->
            fadeIn(
                initialAlpha = 0F, animationSpec = tween(500)
            )
        }, popExitTransition = { _, _ ->
            fadeOut(
                targetAlpha = 0F, animationSpec = tween(500)
            )
        }
    )
    {

        println(">>>>>>>>>addSearchResultScreen = tipo = ")
        ResultadosBusqueda(tipo = 1, datosViewModel = datosViewModel)

    }
    println(">>>>>>>>>dESPUES de composable")
}

@ExperimentalAnimationApi
@Composable
fun rememberAnimatedNavController(): NavHostController {
    val navController = rememberNavController()
    val animatedNavigator = remember(navController) { AnimatedComposeNavigator() }
    return navController.apply {
        navigatorProvider += animatedNavigator
    }
}

class MainActions(navController: NavHostController){
    val navigateToArticle:(String) ->Unit = {postId: String ->
        navController.navigate("${MainDestinations.SINGLE_NEW}/${postId}")
    }
}