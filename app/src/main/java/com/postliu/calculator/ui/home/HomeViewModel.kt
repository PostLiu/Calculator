package com.postliu.calculator.ui.home

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.postliu.calculator.model.KeyEvent
import com.postliu.calculator.model.decodedKeyName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.script.ScriptEngineManager

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    var viewState by mutableStateOf(MainViewState())
        private set

    // 用户操作
    fun dispatch(action: MainViewAction) {
        when (action) {
            // 切换横竖屏
            is MainViewAction.Orientation -> setOrientation()
            // 输入数字
            is MainViewAction.InputNumber -> inputNumber(keyEvent = action.keyEvent)
            // 清除内容
            is MainViewAction.ClearAll -> clearAll()
            // 删除最后一位数
            is MainViewAction.DeleteLast -> deleteLast()
            // 两数相加
            is MainViewAction.Plus -> plusResult()
            // 两数相减
            is MainViewAction.Minus -> minusResult()
            // 两数相乘
            is MainViewAction.Times -> timesResult()
            // 两数相除
            is MainViewAction.Div -> divResult()
            // 计算结果
            is MainViewAction.Equals -> equalsResult()
        }
    }

    private fun setOrientation() {
        val orientation = viewState.orientation
        viewState =
            viewState.copy(orientation = if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    // 计算结果
    private fun equalsResult() {
        kotlin.runCatching {
            var input = viewState.inputNumber
            if (input.contains('×')) {
                input = input.replace('×', '*')
            }
            if (input.contains('÷')) {
                input = input.replace('÷', '/')
            }
            val engine = ScriptEngineManager().getEngineByName("rhino")
            val eval = engine.eval(input).toString()
            eval.toBigDecimal().setScale(2).toString()
        }.onFailure {
            Log.e(TAG, "equalsResult: $it")
        }.onSuccess {
            viewState = viewState.copy(
                calculationResult = it,
                inputNumber = it,
                calculationFormula = ""
            )
        }
    }

    // 两数相除
    private fun divResult() {
        numberFormula("÷")
    }

    // 两数相乘
    private fun timesResult() {
        numberFormula("×")
    }

    // 两数相减
    private fun minusResult() {
        numberFormula("-")
    }

    // 两数相加
    private fun plusResult() {
        numberFormula("+")
    }

    // 解析计算公式
    private fun numberFormula(formula: String) {
        val currentNumber = viewState.inputNumber
        if (currentNumber.contains(formula)) return
        val newInput = currentNumber.plus(formula)
        viewState = viewState.copy(inputNumber = newInput, calculationFormula = formula)
    }

    // 删除最后一位数
    private fun deleteLast() {
        val currentNumber = viewState.inputNumber
        if (currentNumber == "0") return
        val deleteLastNumber = if (currentNumber.length > 1) currentNumber.removeRange(
            currentNumber.length - 1, currentNumber.length
        ) else "0"
        Log.i(
            TAG,
            "deleteLast: deleted last number for result is $deleteLastNumber,before is $currentNumber"
        )
        viewState = viewState.copy(inputNumber = deleteLastNumber)
    }

    // 清除内容
    private fun clearAll() {
        viewState = viewState.copy(
            inputNumber = decodedKeyName(KeyEvent.ZERO()),
            calculationFormula = "",
            calculationResult = "0"
        )
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


}

// 用户操作意图
sealed class MainViewAction {

    // 切换横竖屏
    object Orientation : MainViewAction()

    // 输入数字0-9
    data class InputNumber(val keyEvent: KeyEvent) : MainViewAction()

    // 清除所有内容
    object ClearAll : MainViewAction()

    // 删除最后一个数
    object DeleteLast : MainViewAction()

    // 两数相加
    object Plus : MainViewAction()

    // 两数相减
    object Minus : MainViewAction()

    // 两数相乘
    object Times : MainViewAction()

    // 两数相除
    object Div : MainViewAction()

    // 计算结果
    object Equals : MainViewAction()
}

data class MainViewState(
    // 输入的内容
    val inputNumber: String = decodedKeyName(KeyEvent.ZERO()),
    // 计算公式
    val calculationFormula: String = "",
    // 计算结果
    val calculationResult: String = "0",
    // 横竖屏
    val orientation: Int = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
)