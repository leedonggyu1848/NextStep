package com.example.nextstepwithkotlin.response

import org.apache.logging.log4j.kotlin.logger

/**
 * http응답을 위해 데이터를 저장하는 클래스
 */
class ServerResponse {
    private val log = logger()
    private val builder: StringBuilder = StringBuilder()
    private val headers: MutableMap<String, String> = mutableMapOf()

    /**
     * http 응답 바디
     */
    val body: String
            get() {
                log.trace("get body")
                return builder.toString()
            }

    /**
     * http 응답 상태
     */
    var status: Int = 0

    /**
     * http 응답 버전
     */
    var httpVersion: String = ""

    /**
     * http 응답 상태 메시지
     */
    var phrase: String = ""

    /**
     * http 응답을 위해 컨텐츠를 추가한다.
     * 개행문자와 함께 추가된다.
     * @param s 컨텐츠
     */
    fun appendContent(s: String) {
        log.trace("appendContent: $s")
        builder.append(s)
    }

    private fun isValid(): Boolean {
        return !(status == 0 || httpVersion.isEmpty() || phrase.isEmpty())
    }

    /**
     * http 응답을 위해 데이터를 조합한다.
     *
     * @return http 응답
     * @throws IllegalStateException 응답 데이터가 유효하지 않을 경우
     */
    fun getResponse(): String {
        log.trace("getResponse")
        if (!isValid()) {
            log.warn{"Invalid response status: $status, httpVersion: $httpVersion, phrase: $phrase"}
            throw IllegalStateException("Invalid response")
        }
        return StringBuilder()
            .assembleFirstLine()
            .assembleHeaders()
            .assembleContents()
            .toString()
    }

    private fun StringBuilder.assembleFirstLine(): StringBuilder {
        this.append(httpVersion)
        this.append(" ")
        this.append(status)
        this.append(" ")
        this.append(phrase)
        this.append("\r\n")
        return this
    }

    private fun StringBuilder.assembleHeaders(): StringBuilder {
        headers[CONTENT_LENGTH] = body.toByteArray().size.toString()
        headers.forEach { (key, value) ->
            this.append(key)
            this.append(": ")
            this.append(value)
            this.append("\r\n")
        }
        this.append("\r\n")
        return this
    }

    private fun StringBuilder.assembleContents(): StringBuilder {
        this.append(builder.toString())
        this.append("\r\n")
        return this
    }

    /**
     * http 응답 헤더를 추가한다.
     * @param key 헤더 키
     * @param value 헤더 값
     */
    fun addHeader(key: String, value: String) {
        log.trace{"addHeader: $key, $value"}
        headers[key] = value
    }
}
