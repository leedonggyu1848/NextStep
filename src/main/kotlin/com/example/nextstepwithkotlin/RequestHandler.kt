package com.example.nextstepwithkotlin

import com.example.nextstepwithkotlin.request.ServerRequestParser
import com.example.nextstepwithkotlin.response.ServerDispatcher
import org.apache.logging.log4j.kotlin.logger
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import kotlin.math.min

class RequestHandler(val connection: Socket): Thread() {
    private val log = logger()
    private val parser = ServerRequestParser
    private val dispatcher = ServerDispatcher

    override fun run(): Unit {
        log.debug { "New Client Connect! Connected IP: ${connection.inetAddress}, Port: ${connection.port}" }

        try {
            connection.getInputStream().use {input ->
            connection.getOutputStream().use { output ->
                val request = parser.parse(input)
                request.print()
                val dos = DataOutputStream(output)
                dispatcher.dispatch(request).getResponse()
                    .let {
                        log.trace("response: $it")
                        val bytes = it.toByteArray()
                        val chuckSize = 1024
                        for (i in bytes.indices step chuckSize) {
                            val length = min(chuckSize, bytes.size - i)
                            dos.write(bytes, i, length)
                            dos.flush()
                        }
                    }
            }}
        } catch (e: IOException) {
            log.error { e.message }
        } catch (e: Exception) {
            log.error { e.message }
        }
    }
}