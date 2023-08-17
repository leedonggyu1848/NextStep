package com.example.nextstepwithkotlin.request

import org.apache.logging.log4j.kotlin.logger

/**
 * http요청을 통해 만들어진 요청 객체
 */
class ServerRequest {
    private val log = logger()
    /**
     * http 요청 메소드
     * 기본값은 HttpMethod.NONE
     * @see HttpMethod
     */
    var method: HttpMethod = HttpMethod.NONE
        set(value) {
            log.trace {
                "set method: $value"
            }
            field = value
        }

    /**
     * http 요청 url
     * 기본값은 빈 문자열
     */
    var url: String = ""
        set(value) {
            log.trace {
                "set url: $value"
            }
            field = value
        }

    /**
     * http 요청 버전
     * 기본값은 빈 문자열
     * ex) HTTP/1.1
     */
    var httpVersion: String = ""
        set(value) {
            log.trace {
                "set httpVersion: $value"
            }
            field = value
        }

    private val builder = StringBuilder()

    /**
     * http 요청 전체
     */
    val content: String
        get() = builder.toString()

    /**
     * http 요청 전체 내용을 출력한다.
     */
    fun print() {
        log.trace { "print request" }
        println(builder.toString())
    }

    /**
     * http 요청에 내용을 추가한다.
     * content에 개행문자까지 추가한다.
     * @param content 추가할 내용
     */
    fun appendContent(content: String) {
        log.trace {
            "append content: $content"
        }
        this.builder.append(content)
        this.builder.append("\r\n")
    }
}