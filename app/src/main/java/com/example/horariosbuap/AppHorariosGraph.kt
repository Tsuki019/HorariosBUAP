package com.example.horariosbuap

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.ui.theme.customStuff.screens.*
import com.example.horariosbuap.ui.theme.customStuff.screens.ui.theme.VistaNoticia
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
    const val LOGIN_ROUTE = "ingresar"
    const val REGISTRATION_ROUTE = "registrarse"
    const val SEARCH_RESULT_ROUTE = "resultados_busqueda"
    const val ADD_SUBJECT_ROUTE = "agregar_materia"
    const val INFO_SUBJECT_ROUTE = "informacion_materia"
}

object NavArguments{
    const val SINGLE_NEW_KEY = "noticiaId"
    const val NRC_MATERIA = "nrc"
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
    composable(route = "${MainDestinations.SINGLE_NEW}/{${NavArguments.SINGLE_NEW_KEY}}",
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
            VistaNoticia(noticiaId = backStackEntry.arguments?.getString(NavArguments.SINGLE_NEW_KEY))
            titulos.value = "Noticias"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSearch(
    navController: NavHostController,
    datosViewModel: DatosViewModel,
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

        BuscarScreen(
            onSearchProfesor = {
                datosViewModel.tipoBusqueda.value = 1
                navController.navigate(route = MainDestinations.SEARCH_RESULT_ROUTE)
            },
            onSearchSalon = {
                datosViewModel.tipoBusqueda.value = 2
                navController.navigate(route = MainDestinations.SEARCH_RESULT_ROUTE)
            },
            onSearchMateria = {
                datosViewModel.tipoBusqueda.value = 3
                navController.navigate(route = MainDestinations.SEARCH_RESULT_ROUTE)
            },
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
                popUpTo(MainDestinations.NEWS_ROUTE) } }
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
    navController: NavHostController
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
                    popUpTo(MainDestinations.NEWS_ROUTE)
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
            registerViewModel.state.value = registerViewModel.state.value.copy(successRegister = false)
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
                onBack = { navController.navigate(route = MainDestinations.LOGIN_ROUTE){
                    popUpTo(MainDestinations.LOGIN_ROUTE){inclusive = true}} },
                onLoginWithGoogle = viewModelLogin::loginWithGoogle,
                activity = activity,
                onDismissDialog = viewModel::hideErrorDialog,
                loginViewModel = viewModelLogin
            )
            titulos.value = "Horarios Buap"
        }else{
            navController.navigate(route = MainDestinations.LOGIN_ROUTE){popUpTo(MainDestinations.LOGIN_ROUTE){inclusive = true} }
            titulos.value = "Horarios Buap"
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAddSubject(
    navController: NavHostController,
    datosViewModel: DatosViewModel,
    titulos: MutableState<String>
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
        val actions  = MainActions(navController = navController)

        AgregarMateriasScreen(datosViewModel = datosViewModel, onNavToInfo = actions.navigateToMateria)
        titulos.value = "Agregar Materia"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addInfoSubject(
    navController: NavHostController,
    datosViewModel: DatosViewModel
){
    composable(
        route = MainDestinations.INFO_SUBJECT_ROUTE+"/{${NavArguments.NRC_MATERIA}}"
        , enterTransition = { _, _ ->
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
    }) {backStackEntry ->

        InformacionMateria(
            nrc = backStackEntry.arguments?.getString(NavArguments.NRC_MATERIA),
            datosViewModel = datosViewModel,
            onBack = {navController.popBackStack()}
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSearchResultScreen(
    datosViewModel: DatosViewModel,
    titulos: MutableState<String>,
    navController: NavHostController
) {
     composable(route = MainDestinations.SEARCH_RESULT_ROUTE,
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
        val actions  = MainActions(navController = navController)

        ResultadosBusqueda(datosViewModel = datosViewModel, titulos = titulos, onNavToSubject =  actions.navigateToMateria)
    }


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
        navController.navigate(route = "${MainDestinations.SINGLE_NEW}/${postId}")
    }

    val navigateToMateria:(String) -> Unit = {nrc: String ->
        navController.navigate(route = MainDestinations.INFO_SUBJECT_ROUTE+"/${nrc}")
    }
}