package com.example.nextstepwithkotlin

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

class CalculatorTest {
    var calculator: Calculator = Calculator()

    @BeforeEach
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun addTest() {
        assertThat(calculator.add(1, 1)).isEqualTo(2)
        assertThat(calculator.add(0, 10)).isEqualTo(10)
        assertThat(calculator.add(10, 0)).isEqualTo(10)
        assertThat(calculator.add(6, 3)).isEqualTo(9)
    }

    @Test
    fun subtractTest() {
        assertThat(calculator.subtract(6, 3)).isEqualTo(3)
        assertThat(calculator.subtract(0, 10)).isEqualTo(-10)
        assertThat(calculator.subtract(10, 0)).isEqualTo(10)
    }

    @Test
    fun multiplyTest() {
        assertThat(calculator.multiply(6, 3)).isEqualTo(18)
        assertThat(calculator.multiply(6, 0)).isEqualTo(0)
        assertThat(calculator.multiply(0, 3)).isEqualTo(0)
    }

    @Test
    fun divide() {
        assertThat(calculator.divide(6, 3)).isEqualTo(2)
        assertThat(calculator.divide(0, 1)).isEqualTo(0)
        assertThatThrownBy { calculator.divide(1, 0) }.isInstanceOf(Exception::class.java)
    }
}