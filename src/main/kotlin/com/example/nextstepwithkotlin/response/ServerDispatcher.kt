package com.example.nextstepwithkotlin.response

import com.example.nextstepwithkotlin.request.ServerRequest
import org.apache.logging.log4j.kotlin.logger
import java.io.File

private const val BASE_URL = "src/main/webapp"
/**
 * 요청과 응답을 처리하는 클래스
 */
object ServerDispatcher {
    private val log = logger()

    /**
     * 요청에 대한 응답을 처리하는 메서드
     */
    fun dispatch(request: ServerRequest): ServerResponse {
        val file = request.url.toPath()
        val responseBuilder = ServerResponseBuilder()
        return responseBuilder.apply {
            if (isExistMapping(file)) {
                add200Status()
                addStaticFileBody(file)
            } else {
                add404Status()
                addNotFoundBody()
            }
        }.response
    }

    private fun isExistMapping(file: File): Boolean {
        log.info{ "filePath: ${file.absolutePath}" }
        return file.exists() && file.isFile && file.canRead()
    }
    private fun String.toPath(): File {
        return File("./$BASE_URL$this")
    }
}