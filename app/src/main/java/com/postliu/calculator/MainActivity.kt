package com.postliu.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.postliu.calculator.ui.home.HomeScreen
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
                HomeScreen()
            }
        }
    }
}