package com.example.nextstepwithkotlin.request

class ServerRequest {
    var method: String = "";
    var url: String = "";
    var httpVersion: String = "";

    private val builder = StringBuilder()
    val content: String
        get() = builder.toString()

    fun print() {
        println(builder.toString())
    }

    fun appendContent(content: String) {
        this.builder.append(content)
        this.builder.append("\r\n")
    }
}