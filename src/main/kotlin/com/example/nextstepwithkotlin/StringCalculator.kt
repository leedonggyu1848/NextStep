package com.example.nextstepwithkotlin

import java.util.regex.Pattern

/*
 * 요구사항
 * 전달하는 문자를 구분자로 분리한 후 각 숫자의 합을 구해 반환해야 한다.
 * 쉼표 또는 콜론을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 각 숫자의 합을 반환한다.
 * 앞의 기본 구분자 외에 커스텀 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞부분의 "//"와 "\n" 사이에 위치하는 문자를 커스텀 구분자로 사용한다.
 * 문자열 계산기에 음수를 전달하는 경우 RuntimeException 예외처리를 한다.
 */
class StringCalculator {
    companion object {
        val PATTERN: Pattern = Pattern.compile("//(.)\n(.*)")
        val DEFAULT_DELIMITER: Array<String> = arrayOf(",",":")
    }

    fun add(text: String): Int {
        return if (text.isBlank()) 0
        else parseText(text)
                .map(Integer::parseInt)
                .sumOf { checkNegative(it) }
    }

    private fun checkNegative(number: Int): Int {
        if (number < 0) throw IllegalArgumentException()
        return number
    }

    private fun parseText(text: String): List<String> {
        val matcher = PATTERN.matcher(text)
        return if (matcher.find()) matcher.group(2).split(matcher.group(1)) else text.split(*DEFAULT_DELIMITER)
    }

}