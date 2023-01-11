package com.prvt.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prvt.horariosbuap.viewmodel.DatosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.prvt.horariosbuap.R

@Composable
fun pageNavigator(
    pagina: MutableState<Int>,
    datosViewModel: DatosViewModel,
    ultimo  : Int,
    listState: LazyListState,
    coroutineScope: CoroutineScope
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (ultimo < 6){
            for (i in 1 .. ultimo){
                PageButton(text = "$i", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            }
        }else{
            PageButton(text = "1", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            if (pagina.value < 3){
                PageButton(text = "2", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "3", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "4", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            }
            if (pagina.value > 2 && pagina.value < ultimo - 2){
                PageButton(text = "${pagina.value - 1}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "${pagina.value}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "${pagina.value + 1}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
//                PageButton(text = "${pagina.value + 2}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            }
            if (pagina.value >= ultimo - 2){
                PageButton(text = "${ultimo - 3}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "${ultimo - 2}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "${ultimo - 1}", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
                PageButton(text = "$ultimo", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            }else{
                PageButton(text = "$ultimo", pagina = pagina, datosViewModel = datosViewModel, listState = listState, coroutineScope = coroutineScope)
            }
        }
    }
}

@Composable
fun PageButton(
    text : String,
    pagina : MutableState<Int>,
    datosViewModel : DatosViewModel,
    listState: LazyListState,
    coroutineScope : CoroutineScope
) {



    Box(modifier = Modifier.padding(horizontal = 4.dp)){
        OutlinedMediaButton(
            text = text,
            onClick = {
                datosViewModel.switchStates()
                pagina.value = text.toInt()
                datosViewModel.switchStates()
                coroutineScope.launch {
                    listState.animateScrollToItem(scrollOffset = 0, index = 0)
                }
            },
            buttonColor = if (text == pagina.value.toString()) MaterialTheme.colors.primary
            else MaterialTheme.colors.onSecondary,
            width = 45.dp,
            heigth = 45.dp,
            textStyle = TextStyle(
                fontSize= 11.sp,
                color = MaterialTheme.colors.primary,
                fontFamily = FontFamily(Font(R.font.source_sans_pro))
            )
        )
    }
}