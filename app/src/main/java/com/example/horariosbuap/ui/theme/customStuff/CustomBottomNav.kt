package com.example.horariosbuap.ui.theme.customStuff

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.horariosbuap.R
import javax.xml.transform.Source

@Composable
fun CustomBottomNav(
    currentScreenId: String,
    onItemSelected: (Screen) -> Unit
)
{
    val items = Screen.Items.list

    Row (
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){

        items.forEach{ item->
            CustomBottomNavItem(item = item, isSelected = item.id == currentScreenId) {
            onItemSelected(item)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomBottomNavItem(item:Screen,isSelected:Boolean, onClick:()->Unit) {

    val background = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColer = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground


    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.azulOscuroInstitucional))
                .clickable(onClick = onClick))
        {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Icon(imageVector = item.image,
                    contentDescription = null,
                    tint = colorResource(id = R.color.azulClaroInstitucional)
                )

                AnimatedVisibility(visible = isSelected) {
                    Text(text = item.title, fontFamily= FontFamily(Font(R.font.source_sans_pro)),
                    color = colorResource(id = R.color.azulClaroInstitucional))
                }
            }
        }
    }
}

@Composable
@Preview
fun PreV1() {
    CustomBottomNav(currentScreenId = Screen.Noticias.id){}
}

@Composable
@Preview
fun PreV2() {
    CustomBottomNavItem(item = Screen.Horario, isSelected = true){}
}