package com.example.horariosbuap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberImagePainter
import com.example.horariosbuap.ui.theme.HorariosBUAPTheme
import com.example.horariosbuap.ui.theme.customStuff.CustomBottomNav
import com.example.horariosbuap.ui.theme.customStuff.CustomToolBar
import com.example.horariosbuap.ui.theme.customStuff.screens.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.horariosbuap.core.AppDataBase
import com.example.horariosbuap.core.MateriaTabla
import com.example.horariosbuap.ui.theme.backgroundColorCustom
import com.example.horariosbuap.viewmodel.DatosViewModel
import com.example.horariosbuap.viewmodel.LoginViewModel
import com.example.horariosbuap.viewmodel.RegisterViewModel
import com.example.horariosbuap.viewmodel.UserDataViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestoreSettings


@ExperimentalPagerApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val db = FirebaseFirestore.getInstance()
            var settings = FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
            db.firestoreSettings = settings
            settings = firestoreSettings {
                isPersistenceEnabled = true
            }
            db.firestoreSettings = settings
        }catch (e : IllegalStateException){}


        setContent {

            HorariosBUAPTheme {

                val viewModel : LoginViewModel by viewModels()
                val registerViewModel : RegisterViewModel by viewModels()
                val datosViewModel : DatosViewModel by viewModels()
                val userDataViewModel : UserDataViewModel by viewModels()

                val systemUiController = rememberSystemUiController()
                val navBarColor = colorResource(id = R.color.azulOscuroInstitucional)
                SideEffect {
                    systemUiController.setNavigationBarColor(navBarColor, darkIcons = false)
                }

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: MainDestinations.NEWS_ROUTE
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }
                val titulos = rememberSaveable{ mutableStateOf("Empty") }

                //Revisa si ya ha entrado con un correo
                val auth = Firebase.auth
                val currentUser = auth.currentUser

                if(currentUser != null && currentUser.isEmailVerified){

                    viewModel.LlenarDatosUsuario(currentUser, viewModel.state)
                }

                var listaMaterias = emptyList<MateriaTabla>()
                val database = AppDataBase.getDatabase(this)

                database.materias().getAll().observe(this, Observer {
                    listaMaterias = it
                })


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
                                avatar = if (viewModel.state.value.image == ""){
                                            painterResource(id = R.drawable.default_image)
                                        }else{
                                            rememberImagePainter(data= viewModel.state.value.image)
                                         },
                                email = if (viewModel.state.value.name == ""){
                                            "Usuario invitado"
                                        }else{
                                            viewModel.state.value.name
                                        }
                            )
                        },
                        bottomBar = {
                            when(titulos.value){
                                "Noticias y Calendarios", "Buscar", "Mi horario", "Espacio Libre"->{
                                    CustomBottomNav(
                                        navController = navController,
                                        currentRoute = currentRoute
                                    )
                                }
                                else->{}
                            }
                        },
                        backgroundColor = backgroundColorCustom
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = MainDestinations.NEWS_ROUTE,
                        ){
                            addNews(navController = navController,
                                    titulos = titulos)
                            addSinglePostView(navController = navController,
                                              titulos = titulos)
                            addSearch(navController = navController,
                                      datosViewModel = datosViewModel,
                                      titulos = titulos)
                            addSchedule(navController = navController,
                                        userDataViewModel = userDataViewModel,
                                        titulos = titulos,
                                        datosViewModel = datosViewModel,
                                        coroutineScope = coroutineScope)
                            addFree(navController = navController,
                                    titulos = titulos)
                            addAccountOpt(
                                navController = navController,
                                viewModel = viewModel,
                                titulos = titulos,
                                activity = this@MainActivity,
                                userDataViewModel = userDataViewModel)
                            addSettingsOpt(navController = navController,
                                           titulos = titulos)
                            addAboutOpt(navController = navController,
                                        titulos = titulos)
                            addShareOpt(navController = navController,
                                        titulos = titulos)
                            addExitOpt(navController = navController)
                            addLogin(
                                navController = navController,
                                viewModel = viewModel,
                                registerViewModel = registerViewModel,
                                activity = this@MainActivity,
                                titulos = titulos
                            )
                            addRegister(navController = navController,
                                        activity = this@MainActivity,
                                        viewModelLogin= viewModel,
                                        viewModel = registerViewModel,
                                     titulos = titulos)
                            addAddSubject(
                                navController = navController,
                                datosViewModel = datosViewModel,
                                titulos = titulos,
                                userDataViewModel = userDataViewModel,
                                activity = this@MainActivity
                            )
                            addSearchResultScreen(
                                datosViewModel = datosViewModel,
                                titulos = titulos,
                                navController = navController)
                            addInfoSubject(
                                navController = navController,
                                datosViewModel = datosViewModel,
                                userDataViewModel = userDataViewModel,
                                activity = this@MainActivity
                            )
                            addClassRooms(
                                navController = navController,
                                datosViewModel = datosViewModel
                            )
                            addResetPassword(
                                titulos = titulos,
                                navController = navController
                            )
                            addScheduleDetails(
                                navController = navController,
                                userDataViewModel = userDataViewModel,
                                titulos = titulos,
                                datosViewModel = datosViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val viewModel : LoginViewModel by viewModels()

        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                viewModel.finishLogin(task)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }

        }else if (requestCode == 100 && resultCode == RESULT_OK){
            val fileDescriptor =
                    applicationContext.contentResolver.openAssetFileDescriptor(data?.data!!, "r")
            val fileSize = fileDescriptor!!.length

            if (fileSize <= 100000){
                viewModel.tempImage.value = data?.data!!
            }else{
                Toast.makeText(this, R.string.error_size_image, Toast.LENGTH_LONG).show()
                viewModel.tempImage.value = Uri.EMPTY
            }

        }else{
            viewModel.state.value = viewModel.state.value.copy(errorMessage = R.string.error_login_google)
        }
    }
}