package com.prvt.horariosbuap

import android.app.Activity
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prvt.horariosbuap.ui.theme.customStuff.screens.*
import com.prvt.horariosbuap.viewmodel.*
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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
    const val SINGLE_NEW = "noticia_individual"
    const val LOGIN_ROUTE = "ingresar"
    const val REGISTRATION_ROUTE = "registrarse"
    const val SEARCH_RESULT_ROUTE = "resultados_busqueda"
    const val ADD_SUBJECT_ROUTE = "agregar_materia"
    const val INFO_SUBJECT_ROUTE = "informacion_materia"
    const val RESET_PASSWORD_ROUTE = "reiniciar_contrasena"
    const val CLASSROOMS_ROUTE = "salones_edificio"
    const val SCHEDULE_DETAILS_ROUTE = "detalles_horario"
    const val SPLASH_SCREEN_ROUTE = "splash_screen"
}

object NavArguments{
    const val SINGLE_NEW_KEY = "noticiaId"
    const val NRC_MATERIA = "nrc"
    const val EDIFICIO_ID = "edificioId"
    const val NOMBRE_HORARIO = "nomHorario"
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addNews(
    navController: NavHostController,
    titulos: MutableState<String>,
    datosViewModel: DatosViewModel
){
    composable(route = MainDestinations.NEWS_ROUTE){

        val actions  = MainActions(navController = navController)

        NoticiasScreen(
            navController = navController,
            navigateToArticle = actions.navigateToArticle,
            datosViewModel = datosViewModel
        )
        titulos.value = "Noticias y Calendarios"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSinglePostView(
    navController: NavHostController,
    titulos: MutableState<String>,
    datosViewModel: DatosViewModel
){
    composable(route = "${MainDestinations.SINGLE_NEW}/{${NavArguments.SINGLE_NEW_KEY}}")
    { backStackEntry ->
            VistaNoticia(
                noticiaId = backStackEntry.arguments?.getString(NavArguments.SINGLE_NEW_KEY),
                datosViewModel = datosViewModel
            )
            titulos.value = "Noticias"
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addSearch(
    navController: NavHostController,
    datosViewModel: DatosViewModel,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.SEARCH_ROUTE)
    {

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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addSchedule(
    navController: NavHostController,
    userDataViewModel: UserDataViewModel,
    titulos: MutableState<String>,
    datosViewModel: DatosViewModel,
    coroutineScope: CoroutineScope
){
    val animationState = mutableStateOf(false)
    coroutineScope.launch {
        delay(400)
        animationState.value = true }
    composable(route = MainDestinations.SCHEDULE_ROUTE)
    {
        val actions  = MainActions(navController = navController)

        HorarioScreen(
            isAnimationsOver = animationState,
            navController = navController,
            userDataViewModel = userDataViewModel,
            datosViewModel = datosViewModel,
            onAddSubject = {
                navController.navigate(route = MainDestinations.ADD_SUBJECT_ROUTE)
            },
            onNavToHorario = actions.navigateToHorario
        )
        titulos.value = "Mi horario"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addFree(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.FREE_ROUTE)
    {
        LibreScreen(navController = navController)
        titulos.value = "Espacio Libre"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAccountOpt(
    navController: NavHostController,
    viewModel: LoginViewModel,
    titulos: MutableState<String>,
    activity: Activity,
    userDataViewModel: UserDataViewModel
){
    composable(route = MainDestinations.ACCOUNT_ROUTE)
    {
        MiCuentaOption(
            viewModel = viewModel,
            activity = activity,
            onSignOut = {navController.navigate(route= MainDestinations.LOGIN_ROUTE) {
                popUpTo(MainDestinations.NEWS_ROUTE) } },
            userDataViewModel = userDataViewModel
        )

        titulos.value = "Mi cuenta"

    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSettingsOpt(
    navController: NavHostController,
    titulos: MutableState<String>,
    userDataViewModel: UserDataViewModel
){
    composable(route = MainDestinations.SETTINGS_ROUTE)
    {
        AjustesOption(userDataViewModel = userDataViewModel)
        titulos.value = "Ajustes"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addAboutOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.ABOUT_ROUTE)
    {
        AcercaDeOption()
        titulos.value = "Acerca de la Aplicacion"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addShareOpt(
    navController: NavHostController,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.SHARE_ROUTE)
    {
        CompartirOption()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addExitOpt(
    navController: NavHostController
){
    composable(route = MainDestinations.EXIT_ROUTE)
    {
        SalirOption()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addLogin(
    navController: NavHostController,
    viewModel : LoginViewModel,
    registerViewModel: RegisterViewModel,
    userDataViewModel: UserDataViewModel,
    activity: Activity,
    titulos: MutableState<String>
){
    composable(route = MainDestinations.LOGIN_ROUTE)
    {

        if (viewModel.state.value.successLogin){
            LaunchedEffect(key1 = Unit){
                navController.navigate(route = MainDestinations.ACCOUNT_ROUTE){
                    popUpTo(MainDestinations.NEWS_ROUTE)
                }
            }
        }else{
            LoginScreen(state = viewModel.state.value,
                        registerViewModel = registerViewModel,
                        userDataViewModel = userDataViewModel,
                        onLogin = viewModel::login,
                        onLoginWithGoogle = viewModel::loginWithGoogle,
                        activity = activity,
                        onDissmisDialog = viewModel::hideErrorDialog,
                        onNavigateToRegister = {navController.navigate(route = MainDestinations.REGISTRATION_ROUTE)},
                        onForgetPassword = {navController.navigate(route = MainDestinations.RESET_PASSWORD_ROUTE)}
            )
            registerViewModel.state.value = registerViewModel.state.value.copy(successRegister = false)
            titulos.value = "Horarios Buap"
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addResetPassword(
    titulos: MutableState<String>,
    navController: NavHostController
){
    composable(route = MainDestinations.RESET_PASSWORD_ROUTE)
    {

        titulos.value = "Reiniciar Contraseña"

        ReestablecerContrasena(onBack = {navController.popBackStack()})
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addRegister(
    activity: Activity,
    viewModelLogin : LoginViewModel,
    viewModel : RegisterViewModel,
    navController: NavHostController,
    titulos: MutableState<String>,
    userDataViewModel: UserDataViewModel
){
    composable(route = MainDestinations.REGISTRATION_ROUTE)
    {

        if(!viewModel.state.value.successRegister){
            RegistrationScreen(
                state = viewModel.state.value,
                onRegister = viewModel::register,
                onBack = {navController.popBackStack()},
                activity = activity,
                onDismissDialog = viewModel::hideErrorDialog,
                loginViewModel = viewModelLogin,
                userDataViewModel = userDataViewModel
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
    titulos: MutableState<String>,
    userDataViewModel: UserDataViewModel,
    activity: Activity
) {
    composable(route = MainDestinations.ADD_SUBJECT_ROUTE+"/{${NavArguments.NOMBRE_HORARIO}}")
    {
        val actions  = MainActions(navController = navController)

        AgregarMateriasScreen(
            datosViewModel = datosViewModel,
            onNavToInfo = actions.navigateToMateria,
            nombreHorario = it.arguments?.getString(NavArguments.NOMBRE_HORARIO)!!,
            userDataViewModel = userDataViewModel,
            activity = activity
        )
        titulos.value = "Agregar Materia"
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addInfoSubject(
    navController: NavHostController,
    datosViewModel: DatosViewModel,
    userDataViewModel: UserDataViewModel,
    activity: Activity
){
    composable(
        route = MainDestinations.INFO_SUBJECT_ROUTE+"/{${NavArguments.NRC_MATERIA}}/{${NavArguments.NOMBRE_HORARIO}}")
    {backStackEntry ->

        InformacionMateria(
            nrc = backStackEntry.arguments?.getString(NavArguments.NRC_MATERIA),
            datosViewModel = datosViewModel,
            onBack = {navController.popBackStack()},
            nombreHorario = backStackEntry.arguments?.getString(NavArguments.NOMBRE_HORARIO)!!,
            userDataViewModel = userDataViewModel,
            activity = activity
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addClassRooms(
    navController: NavHostController,
    datosViewModel: DatosViewModel
){
    composable(
        route = MainDestinations.CLASSROOMS_ROUTE+"/{${NavArguments.EDIFICIO_ID}}")
    {backStackEntry ->

        SalonesPorEdificio(
            edificio = backStackEntry.arguments?.getString(NavArguments.EDIFICIO_ID),
            salones = datosViewModel.salones,
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
     composable(route = MainDestinations.SEARCH_RESULT_ROUTE)
    {
        val actions  = MainActions(navController = navController)

        ResultadosBusqueda(
            datosViewModel = datosViewModel,
            titulos = titulos,
            onNavToSubject = actions.navigateToMateria,
            onNavToClassRooms = actions.navigateToSalones
        )
    }


}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addScheduleDetails(
    navController: NavHostController,
    userDataViewModel: UserDataViewModel,
    titulos: MutableState<String>,
    datosViewModel: DatosViewModel
){
    composable(route = MainDestinations.SCHEDULE_DETAILS_ROUTE+"/{${NavArguments.NOMBRE_HORARIO}}")
    {backStackEntry ->

            Log.w("Llamada", "DetallesHorario")
            titulos.value = backStackEntry.arguments?.getString(NavArguments.NOMBRE_HORARIO)!!+" "
            val actions = MainActions(navController = navController)

            DetallesHorarioScreen(
                nombreHorario = backStackEntry.arguments?.getString(NavArguments.NOMBRE_HORARIO),
                userDataViewModel = userDataViewModel,
                onNavToAddSubject = actions.navigateToAgregarMateria,
                datosViewModel = datosViewModel
            )
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

    val navigateToMateria:(String, String) -> Unit = {nrc: String, nombreHorario: String ->
        navController.navigate(route = MainDestinations.INFO_SUBJECT_ROUTE+"/${nrc}/${nombreHorario}")
    }

    val navigateToSalones:(String) -> Unit = {edificioId: String ->
        navController.navigate(route = MainDestinations.CLASSROOMS_ROUTE+"/${edificioId}")
    }

    val navigateToHorario:(String) -> Unit = {nomHorario: String ->
        navController.navigate(route = MainDestinations.SCHEDULE_DETAILS_ROUTE+"/${nomHorario}")
    }

    val navigateToAgregarMateria:(String) -> Unit = {nomHorario: String ->
        navController.navigate(route = MainDestinations.ADD_SUBJECT_ROUTE+"/${nomHorario}")
    }
}