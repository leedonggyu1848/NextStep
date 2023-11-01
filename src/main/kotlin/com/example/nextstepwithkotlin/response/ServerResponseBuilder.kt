package com.example.nextstepwithkotlin.response

import java.io.File

const val HTML_UTF_8 = "text/html; charset=UTF-8"
const val HTTP_VERSION_1 = "HTTP/1.1"


class ServerResponseBuilder {
    val response = ServerResponse()

    fun add200Status() {
        response.apply {
            status = HttpStatus.OK.code
            httpVersion = HTTP_VERSION_1
            phrase = HttpStatus.OK.phrase
            addHeader(CONTENT_TYPE, HTML_UTF_8)
        }
    }

    fun add404Status() {
        response.apply {
            status = HttpStatus.NOT_FOUND.code
            httpVersion = HTTP_VERSION_1
            phrase = HttpStatus.NOT_FOUND.phrase
        }
    }

    fun addStaticFileBody(file: File) {
        response.apply {
            appendContent(file.readText(Charsets.UTF_8))
        }
    }

    fun addNotFoundBody() {
        response.apply {
            addHeader(CONTENT_TYPE, HTML_UTF_8)
            appendContent("<html><body>페이지 없음</body></html>")
        }
    }
}