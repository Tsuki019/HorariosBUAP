package com.example.horariosbuap.ui.theme.customStuff.components

import androidx.compose.foundation.background
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horariosbuap.ui.theme.backgroundColorCustom
import com.example.horariosbuap.ui.theme.primaryColorCustom
import com.example.horariosbuap.ui.theme.secundaryColorCustom
import com.example.horariosbuap.ui.theme.customStuff.sansPro
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabMenu(
    pagerState : PagerState,
    listElements : List<String>,
    coroutineScope: CoroutineScope
) {
    
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        divider = { TabRowDefaults.Divider(thickness = 1.dp, color = primaryColorCustom) },
        indicator = {tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions),
                height = 2.dp,
                color = secundaryColorCustom
            )
        }
    ) {
        listElements.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.background(if (pagerState.currentPage == index) primaryColorCustom else primaryColorCustom.copy(0.9f)),
                selectedContentColor = secundaryColorCustom,
                unselectedContentColor = secundaryColorCustom.copy(0.8f),
                text = {
                    Text(
                        text = listElements[index],
                        fontFamily = sansPro,
                        fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 20.sp
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}