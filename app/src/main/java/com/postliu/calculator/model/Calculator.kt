package com.postliu.calculator.model

// 计算器类型
sealed class Calculator {
    // 标准计算器
    data class Default(val name: String) : Calculator()

    // 科学计算器
    data class Science(val name: String) : Calculator()
}