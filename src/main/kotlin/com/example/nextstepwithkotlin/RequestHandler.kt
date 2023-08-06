package com.example.nextstepwithkotlin

import org.apache.logging.log4j.kotlin.logger
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class RequestHandler(val connection: Socket): Thread() {
    val log = logger()

    override fun run(): Unit {
        log.debug { "New Client Connect! Connected IP: ${connection.inetAddress}, Port: ${connection.port}" }

        try {
            connection.getInputStream().use {input ->
            connection.getOutputStream().use { output ->
                val dos = DataOutputStream(output)
                val body = "Hello World!".toByteArray()
                response200Header(dos, body.size)
                responseBody(dos, body)
            }}
        } catch (e: IOException) {
            log.error { e.message }
        }
    }

    private fun response200Header(dos: DataOutputStream, lengthOfBodyContent: Int): Unit {
        dos.writeBytes("HTTP/1.1 200 OK \r\n")
        dos.writeBytes("Content-Type: text//html;charset=utf-8\r\n")
        dos.writeBytes("Content-Length: ${lengthOfBodyContent}\r\n")
        dos.writeBytes("\r\n")
    }

    private fun responseBody(dos: DataOutputStream, body: ByteArray): Unit {
        dos.write(body, 0, body.size)
        dos.writeBytes("\r\n")
        dos.flush()
    }

}