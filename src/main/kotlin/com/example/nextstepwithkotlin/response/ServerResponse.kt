package com.example.nextstepwithkotlin.response

import org.apache.logging.log4j.kotlin.logger

class ServerResponse {
    private val log = logger()
    private val builder: StringBuilder = StringBuilder()
    private val headers: MutableMap<String, String> = mutableMapOf()
    val body: String
            get() = builder.toString()
    var status: Int = 0
    var httpVersion: String = ""
    var phrase: String = ""

    fun appendContent(s: String) {
        builder.append(s)
        builder.append("\r\n")
    }

    fun isValid(): Boolean {
        return !(status == 0 || httpVersion.isEmpty() || phrase.isEmpty())
    }

    fun getResponse(): String {
        if (!isValid()) {
            throw IllegalStateException("Invalid response")
        }
        val response = StringBuilder()
        assembleFirstLine(response)
        assembleHeaders(response)
        response.append(builder.toString())
        return response.toString()
    }

    private fun assembleFirstLine(response: StringBuilder) {
        response.append(httpVersion)
        response.append(" ")
        response.append(status)
        response.append(" ")
        response.append(phrase)
        response.append("\r\n")
    }

    private fun assembleHeaders(response: StringBuilder) {
        headers.forEach { (key, value) ->
            response.append(key)
            response.append(": ")
            response.append(value)
            response.append("\r\n")
        }
        response.append("\r\n")
    }

    fun addHeader(key: String, value: String) {
        headers[key] = value
    }
}
