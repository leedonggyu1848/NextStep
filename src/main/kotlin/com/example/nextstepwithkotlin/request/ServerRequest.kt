package com.example.nextstepwithkotlin.request

/**
 * http요청을 통해 만들어진 요청 객체
 */
class ServerRequest {
    /**
     * http 요청 메소드
     * 기본값은 HttpMethod.NONE
     * @see HttpMethod
     */
    var method: HttpMethod = HttpMethod.NONE

    /**
     * http 요청 url
     * 기본값은 빈 문자열
     */
    var url: String = ""

    /**
     * http 요청 버전
     * 기본값은 빈 문자열
     * ex) HTTP/1.1
     */
    var httpVersion: String = ""

    private val builder = StringBuilder()

    /**
     * http 요청 body
     */
    val content: String
        get() = builder.toString()

    /**
     * http 요청 body를 출력한다.
     */
    fun print() {
        println(builder.toString())
    }

    /**
     * http 요청 body에 내용을 추가한다.
     * content에 개행문자까지 추가한다.
     * @param content 추가할 내용
     */
    fun appendContent(content: String) {
        this.builder.append(content)
        this.builder.append("\r\n")
    }
}