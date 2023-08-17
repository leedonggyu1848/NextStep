package com.example.nextstepwithkotlin.request

import org.apache.logging.log4j.kotlin.logger
import java.io.InputStream

/**
 * http요청을 파싱
 */
object ServerRequestParser {
    private val log = logger()
    /**
     * http 요청을 파싱한다.
     * @param input http 요청
     * @return 파싱된 요청 객체
     * @see ServerRequest
     * @throws ParseError http 요청이 잘못된 경우
     */
    fun parse(input: InputStream): ServerRequest {
        log.info("parse request")
        val request = ServerRequest()
        val br = input.bufferedReader()
        generateSequence { br.readLine() }
                    .takeWhile { it.isNotEmpty() }
                    .forEach { request.appendContent(it) }
        return parseFirstLine(request)
    }

    private fun parseFirstLine(request: ServerRequest): ServerRequest {
        val firstLine = request.content.split("\r\n")[0]
        val tokens = firstLine.split(" ")
        if (tokens.size != 3) {
            throw ParseError("invalid request line: $firstLine")
        }
        tokens[0].also { request.method = HttpMethod.valueOf(it) }
        tokens[1].also { request.url = it }
        tokens[2].also { request.httpVersion = it }
        return request
    }
}