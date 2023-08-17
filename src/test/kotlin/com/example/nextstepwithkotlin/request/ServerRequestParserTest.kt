package com.example.nextstepwithkotlin.request

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTP

class ServerRequestParserTest : AnnotationSpec() {
    val parser = ServerRequestParser

    @Test
    fun `간단한 request parsing`() {
        val input = """GET /index.html HTTP/1.1
            |Host: localhost:8080
            |Connection: keep-alive
            |Accept: */*
            
            |""".trimMargin().replace("\n", "\r\n")
        val request = parser.parse(input.byteInputStream())

        request.method shouldBe HttpMethod.GET
        request.url shouldBe "/index.html"
        request.httpVersion shouldBe "HTTP/1.1"
        request.content shouldBe input
    }

    @Test
    fun `복잡한 request parsing`() {
        val input = """POST /index.html HTTP/1.1
            |Host: localhost:8080
            |Connection: keep-alive
            |Accept: */*
            |Content-Length: 5
            
            |Hello
            |""".trimMargin().replace("\n", "\r\n")
        val request = parser.parse(input.byteInputStream())

        request.method shouldBe HttpMethod.POST
        request.url shouldBe "/index.html"
        request.httpVersion shouldBe "HTTP/1.1"
        request.content shouldBe input
    }
}
