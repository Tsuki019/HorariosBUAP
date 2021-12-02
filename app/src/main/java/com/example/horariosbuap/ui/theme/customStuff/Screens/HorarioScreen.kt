package com.example.horariosbuap.ui.theme.customStuff.Screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.horariosbuap.MainDestinations
import com.example.horariosbuap.ui.theme.customStuff.Screen
import com.example.horariosbuap.ui.theme.dataBase.LoginViewModel

@Composable
fun HorarioScreen(
    navController: NavController
//    scaffoldState: ScaffoldState,
//    openDrawer: ()->Unit
) {

        Text(text = "Para acceder a las funciones de esta ventana, por favor ingrese a su cuenta")
}

//@Composable
//fun LoginScreen() {
//
//    val sourceSans = FontFamily(Font(R.font.source_sans_pro))
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .background(colorResource(id = R.color.azulOscuroInstitucional))
//        .padding(10.dp))
//    {
//        Image(modifier = Modifier
//            .align(alignment = Alignment.CenterHorizontally)
//            .padding(top = 70.dp),
//              painter = painterResource(id = R.drawable.logo_horariosbuap),
//              contentDescription = "")
//        Divider(modifier = Modifier.padding(20.dp))
//        Text(modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
//             color = Color.White,
//             text = "Ingresar",
//             fontSize = 45.sp,
//             fontFamily = sourceSans)
//        Divider(modifier = Modifier.padding(10.dp))
//
//        Button(onClick = { /*TODO*/ },)
//        {
//            Icon(painter = painterResource(id = R.drawable.ic_google),
//                 contentDescription ="")
//            Text(text = "Ingresar con Google")
//        }
//    }
//}


//@Preview
//@Composable
//fun TestHorario() {
//    val navController = rememberNavController()
//    val scaffoldState = rememberScaffoldState()
//    HorarioScreen(navController = navController,
//                  scaffoldState = scaffoldState) {
//
//    }
//}
