package com.example.nextstepwithkotlin.response

import com.example.nextstepwithkotlin.request.ServerRequest
import org.apache.logging.log4j.kotlin.logger
import java.io.File

object ServerDispatcher {
    private val log = logger()
    private const val baseUrl = "src/main/webapp"
    private const val charset = "utf-8"

    fun dispatch(request: ServerRequest): ServerResponse {
        val response = ServerResponse()
        val file = generatePathFromUrl(request.url)
        if (isExistMapping(file)) {
            add200Status(response)
            addStaticFileBody(response, file)
        } else {
            response.status = 404
            response.httpVersion = "HTTP/1.1"
            response.phrase = "Not Found"

            response.addHeader("Content-Type", "text/html;charset=${charset}")
            response.appendContent("<html><body>페이지 없음</body></html>")
            response.addHeader("Content-Length", response.body.length.toString())
        }
        log.info(response.getResponse())
        return response
    }

    private fun isExistMapping(file: File): Boolean {
        log.info{ "filePath: ${file.absolutePath}" }
        return file.exists() && file.isFile && file.canRead()
    }

    private fun add200Status(response: ServerResponse) {
        response.status = 200
        response.httpVersion = "HTTP/1.1"
        response.phrase = "OK"
        response.addHeader("Content-Type", "text/html;charset=${charset}")
    }

    private fun addStaticFileBody(response: ServerResponse, file: File) {
        response.appendContent(file.readText(Charsets.UTF_8))
        response.addHeader("Content-Length", response.body.length.toString())
    }

    private fun generatePathFromUrl(url: String): File {
        return File("./$baseUrl$url")
    }
}