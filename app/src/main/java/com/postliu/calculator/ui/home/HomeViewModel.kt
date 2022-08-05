package com.postliu.calculator.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.postliu.calculator.model.Calculator
import com.postliu.calculator.model.KeyEvent
import com.postliu.calculator.model.decodedKeyName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var viewState by mutableStateOf(MainViewState())
        private set

    // 默认的计算器类型列表
    val calculators = listOf(
        Calculator.Default("标准"), Calculator.Science("科学")
    )

    // 用户操作
    fun dispatch(action: MainViewAction) {
        when (action) {
            // 切换计算器
            is MainViewAction.ClickCalculator -> clickCalculator(name = action.name)
            // 输入数字
            is MainViewAction.InputNumber -> inputNumber(keyEvent = action.keyEvent)
            // 清除内容
            is MainViewAction.ClearAll -> clearAll()
        }
    }

    // 切换计算器
    private fun clickCalculator(name: String) {
        viewState = viewState.copy(clickCalculator = name)
    }

    // 输入内容
    private fun inputNumber(keyEvent: KeyEvent) {
        var number: String
        // 输入的是数字，则需要append
        val newNumber = decodedKeyName(keyEvent)
        val oldNumber = viewState.inputNumber
        number = oldNumber
        number = if (number.length == 1 && number == "0") {
            newNumber
        } else {
            if (number.length > 22) return
            oldNumber.plus(newNumber)
        }
        viewState = viewState.copy(inputNumber = number)
    }

    // 清除内容
    private fun clearAll() {
        viewState = viewState.copy(inputNumber = decodedKeyName(KeyEvent.ZERO()))
    }
}

// 用户操作意图
sealed class MainViewAction {
    // 切换计算器
    data class ClickCalculator(val name: String) : MainViewAction()

    // 输入数字0-9
    data class InputNumber(val keyEvent: KeyEvent) : MainViewAction()

    // 清除所有内容
    object ClearAll : MainViewAction()
}

data class MainViewState(
    val clickCalculator: String = "标准", val inputNumber: String = decodedKeyName(KeyEvent.ZERO())
)