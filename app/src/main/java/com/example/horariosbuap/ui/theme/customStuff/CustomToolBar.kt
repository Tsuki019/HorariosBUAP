package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomToolBar(title: String, scaffoldState: ScaffoldState, scope: CoroutineScope) {
    TopAppBar(
        title = {
            Row(modifier= Modifier
                .fillMaxSize()
                .padding(end = 60.dp)
                ,verticalAlignment = Alignment.CenterVertically ) {
                Text(modifier= Modifier.fillMaxWidth(),
                    text = title,
                     fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                     textAlign= TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open()
                println("PRECIONADO")}  }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = colorResource(id = R.color.azulOscuroInstitucional),
        contentColor = colorResource(id = R.color.azulClaroInstitucional),
        elevation = 12.dp,
    )
}


@Composable
fun NavDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState, email: String) {

    Surface(modifier= Modifier
        .fillMaxSize(),
            color= colorResource(id = R.color.azulOscuroInstitucional)) {

        Column(modifier = Modifier
            .fillMaxWidth()) {

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start){
                Image(modifier= Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(shape = CircleShape),
                      painter = painterResource(id = R.drawable.hatsune_test),
                      contentDescription = "",
                      contentScale = ContentScale.Crop
                )
                Text(
                    textAlign= TextAlign.Right,
                    text = email,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                    fontSize = 15.sp,
                    color = Color.White)

            }

            Divider(modifier = Modifier.height(2.dp), color = colorResource(id = R.color.azulClaroInstitucional))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.ManageAccounts, contentDescription = "",
                     modifier= Modifier.sizeIn(minWidth = 25.dp, maxWidth = 50.dp, minHeight = 25.dp, maxHeight = 50.dp),
                     tint= Color.White)

                Text(text = "Mi cuenta", modifier = Modifier
                    .clickable { scope.launch { scaffoldState.drawerState.close() } }
                    .padding(start = 10.dp),
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                     fontSize = 30.sp,
                     color= Color.White)
            }

            Divider(color= colorResource(id = R.color.azulClaroInstitucional))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Settings, contentDescription = "",
                     modifier= Modifier.sizeIn(minWidth = 25.dp, maxWidth = 50.dp, minHeight = 25.dp, maxHeight = 50.dp),
                     tint= Color.White)

                Text(text = "Ajustes", modifier = Modifier
                    .clickable { scope.launch { scaffoldState.drawerState.close() } }
                    .padding(start = 10.dp),
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                     fontSize = 30.sp,
                     color= Color.White)
            }

            Divider(color= colorResource(id = R.color.azulClaroInstitucional))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Info, contentDescription = "",
                     modifier= Modifier.sizeIn(minWidth = 25.dp, maxWidth = 50.dp, minHeight = 25.dp, maxHeight = 50.dp),
                     tint= Color.White)

                Text(text= "Acerca de", modifier = Modifier
                    .clickable { scope.launch { scaffoldState.drawerState.close() } }
                    .padding(start = 10.dp),
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                     fontSize = 30.sp,
                     color= Color.White)
            }

            Divider(color= colorResource(id = R.color.azulClaroInstitucional))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.DoorBack, contentDescription = "",
                     modifier= Modifier.sizeIn(minWidth = 25.dp, maxWidth = 50.dp, minHeight = 25.dp, maxHeight = 50.dp),
                     tint= Color.White)

                Text(text = "Salir", modifier = Modifier
                    .clickable { scope.launch { scaffoldState.drawerState.close() } }
                    .padding(start = 10.dp),
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily(Font(R.font.source_sans_pro)),
                     fontSize = 30.sp,
                     color= Color.White)
            }

            Divider(color= colorResource(id = R.color.azulClaroInstitucional))
        }
    }
    
}

//@Preview
//@Composable
//fun PrevToolBar() {
//    CustomToolBar(title = "Horarios BUAP", )
//}

