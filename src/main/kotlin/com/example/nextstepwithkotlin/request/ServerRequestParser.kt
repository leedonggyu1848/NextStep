package com.example.nextstepwithkotlin.request

import java.io.InputStream

object ServerRequestParser {
    fun parse(input: InputStream): ServerRequest {
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
        tokens[0].also { request.method = it }
        tokens[1].also { request.url = it }
        tokens[2].also { request.httpVersion = it }
        return request
    }
}