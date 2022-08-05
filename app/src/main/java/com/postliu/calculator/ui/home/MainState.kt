package com.postliu.calculator.ui.home

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.postliu.calculator.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    private val scaffoldState: ScaffoldState,
    private val scope: CoroutineScope,
) {

    // 打开侧边栏
    fun openDrawer() = scope.launch {
        scaffoldState.drawerState.open()
    }

    // 关闭侧边栏
    fun closeDrawer() = scope.launch {
        scaffoldState.drawerState.close()
    }
}

@Composable
fun rememberMainState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    viewModel: MainViewModel = hiltViewModel()
): MainState {
    return remember { MainState(scaffoldState, scope) }
}