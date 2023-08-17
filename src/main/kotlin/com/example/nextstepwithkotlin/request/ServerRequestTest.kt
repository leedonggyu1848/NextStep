package com.example.nextstepwithkotlin.request

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ServerRequestTest: AnnotationSpec() {
    private var request = ServerRequest()

    @BeforeEach
    fun setUp() {
        request = ServerRequest()
    }

    @Test
    fun `간단한 url 설정`() {
        val url = "http://localhost:8080"
        request.url = url

        request.url shouldBe "http://localhost:8080"
    }

    @Test
    fun `간단한 http method 설정`() {
        request.method = HttpMethod.GET

        request.method shouldBe HttpMethod.GET
    }

    @Test
    fun `간단한 http version 설정`() {
        request.httpVersion = "HTTP/1.1"

        request.httpVersion shouldBe "HTTP/1.1"
    }

    @Test
    fun `간단한 content 설정`() {
        request.appendContent("Hello World")

        request.content shouldBe "Hello World\r\n"
    }

    @Test
    fun `복잡한 content 설정`() {
        request.appendContent("Hello World")
        request.appendContent("Hello World2")

        request.content shouldBe "Hello World\r\nHello World2\r\n"
    }
}
