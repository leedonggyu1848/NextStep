package com.example.nextstepwithkotlin

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StringCalculatorTest {
    var cal: StringCalculator = StringCalculator()

    @BeforeEach
    fun setUp() {
        cal = StringCalculator();
    }

    @Test
    fun add_빈문자() {
        assertThat(cal.add("")).isEqualTo(0);
    }

    @Test
    fun add_숫자하나() {
        assertThat(cal.add("1")).isEqualTo(1);
    }

    @Test
    fun add_쉼표구분자() {
        assertThat(cal.add("1,2")).isEqualTo(3);
    }

    @Test
    fun add_쉼표_또는_콜론_구분자() {
        assertThat(cal.add("1,2:3")).isEqualTo(6);
    }

    @Test
    fun add_custom_구분자() {
        assertThat(cal.add("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    fun add_negative() {
        assertThatThrownBy{cal.add("-1,2,3")}.isInstanceOf(RuntimeException::class.java);
    }
}