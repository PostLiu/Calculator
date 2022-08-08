package com.postliu.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.postliu.calculator.ui.home.HomeScreen
import com.postliu.calculator.ui.home.MainViewModel
import com.postliu.calculator.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CalculatorTheme {
                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colors.primary
                SideEffect {
                    systemUiController.setStatusBarColor(statusBarColor)
                }
                val viewModel: MainViewModel = hiltViewModel()
                val orientation = viewModel.viewState.orientation
                requestedOrientation = orientation
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}