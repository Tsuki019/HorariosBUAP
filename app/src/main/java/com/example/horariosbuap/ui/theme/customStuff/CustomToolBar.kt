package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun CustomToolBar(backgroundColor: Color = MaterialTheme.colors.background,
                  elevation: Dp = 6.dp,
                  modifier: Modifier = Modifier,
                  title: String,
                  scaffoldState: ScaffoldState,
                  icon: @Composable (() -> Unit)? = null){

    Surface(color = backgroundColor,
            elevation = elevation,
            modifier = modifier,
    ) {
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
            navigationIcon = icon,
            backgroundColor = colorResource(id = R.color.azulOscuroInstitucional),
            contentColor = colorResource(id = R.color.azulClaroInstitucional),
            elevation = 12.dp,
        )

    }
}


//@Preview
//@Composable
//fun PrevToolBar() {
//    CustomToolBar(title = "Horarios BUAP", )
//}

