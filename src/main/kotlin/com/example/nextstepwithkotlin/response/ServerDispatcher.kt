package com.example.nextstepwithkotlin.response

import com.example.nextstepwithkotlin.request.ServerRequest
import org.apache.logging.log4j.kotlin.logger
import java.nio.file.Files
import java.nio.file.Paths

object ServerDispatcher {
    private val log = logger()
    fun dispatch(request: ServerRequest): ServerResponse {
        val response = ServerResponse()
        if (isExistMapping(request.url)) {
            add200Status(response)
            addStaticFileBody(response, request.url)

        } else {
            response.status = 404
            response.httpVersion = "HTTP/1.1"
            response.phrase = "Not Found"
            response.addHeader("Content-Type", "text/html;charset=utf-8")
            response.appendContent("<html><body>Not Found</body></html>")
            response.addHeader("Content-Length", response.body.length.toString())
        }
        log.info(response.getResponse())
        return response
    }

    private fun isExistMapping(url: String): Boolean {
        return Files.exists(Paths.get("./webapp${url}"))
    }

    private fun add200Status(response: ServerResponse) {
        response.status = 200
        response.httpVersion = "HTTP/1.1"
        response.phrase = "OK"
        response.addHeader("Content-Type", "text/html;charset=utf-8")
    }

    private fun addStaticFileBody(response: ServerResponse, url: String) {
        response.withBytes(Files.readAllBytes(Paths.get("./webapp${url}")))
        response.addHeader("Content-Length", response.body.length.toString())
    }

}