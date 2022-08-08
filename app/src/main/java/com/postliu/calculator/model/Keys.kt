package com.postliu.calculator.model

sealed class KeyEvent {
    // 切换横屏
    data class Orientation(val name: String = "&#xe664;") : KeyEvent()

    // 清空
    data class C(val name: String = "C") : KeyEvent()

    // 删除
    data class DEL(val name: String = "DEL") : KeyEvent()

    // 加
    data class PLUS(val name: String = "+") : KeyEvent()

    // 减
    data class MINUS(val name: String = "-") : KeyEvent()

    // 乘
    data class TIMES(val name: String = "×") : KeyEvent()

    // 除以
    data class DIV(val name: String = "÷") : KeyEvent()

    // 等于
    data class EQUALS(val name: String = "=") : KeyEvent()

    // 0
    data class ZERO(val name: String = "0") : KeyEvent()

    // 1
    data class ONE(val name: String = "1") : KeyEvent()

    // 2
    data class TWO(val name: String = "2") : KeyEvent()

    // 3
    data class THREE(val name: String = "3") : KeyEvent()

    // 4
    data class FOUR(val name: String = "4") : KeyEvent()

    // 5
    data class FIVE(val name: String = "5") : KeyEvent()

    // 6
    data class SIX(val name: String = "6") : KeyEvent()

    // 7
    data class SEVEN(val name: String = "7") : KeyEvent()

    // 8
    data class EIGHT(val name: String = "8") : KeyEvent()

    // 9
    data class NINE(val name: String = "9") : KeyEvent()
}


val defaultKeyEvent = listOf(

    KeyEvent.Orientation(),
    KeyEvent.C(),
    KeyEvent.DEL(),
    KeyEvent.EQUALS(),

    KeyEvent.SEVEN(),
    KeyEvent.EIGHT(),
    KeyEvent.NINE(),
    KeyEvent.DIV(),

    KeyEvent.FOUR(),
    KeyEvent.FIVE(),
    KeyEvent.SIX(),
    KeyEvent.TIMES(),

    KeyEvent.ONE(),
    KeyEvent.TWO(),
    KeyEvent.THREE(),
    KeyEvent.MINUS(),


    KeyEvent.ZERO(),
    KeyEvent.PLUS(),
)

val numberInKey = arrayOf(
    KeyEvent.ZERO(),
    KeyEvent.ONE(),
    KeyEvent.TWO(),
    KeyEvent.THREE(),
    KeyEvent.FOUR(),
    KeyEvent.FIVE(),
    KeyEvent.SIX(),
    KeyEvent.SEVEN(),
    KeyEvent.EIGHT(),
    KeyEvent.NINE()
)

// 读取KeyEvent的值
fun decodedKeyName(keyEvent: KeyEvent) = when (keyEvent) {
    is KeyEvent.C -> keyEvent.name
    is KeyEvent.DEL -> keyEvent.name
    is KeyEvent.DIV -> keyEvent.name
    is KeyEvent.EIGHT -> keyEvent.name
    is KeyEvent.EQUALS -> keyEvent.name
    is KeyEvent.FIVE -> keyEvent.name
    is KeyEvent.FOUR -> keyEvent.name
    is KeyEvent.MINUS -> keyEvent.name
    is KeyEvent.NINE -> keyEvent.name
    is KeyEvent.Orientation -> keyEvent.name
    is KeyEvent.ONE -> keyEvent.name
    is KeyEvent.PLUS -> keyEvent.name
    is KeyEvent.SEVEN -> keyEvent.name
    is KeyEvent.SIX -> keyEvent.name
    is KeyEvent.THREE -> keyEvent.name
    is KeyEvent.TIMES -> keyEvent.name
    is KeyEvent.TWO -> keyEvent.name
    is KeyEvent.ZERO -> keyEvent.name
}