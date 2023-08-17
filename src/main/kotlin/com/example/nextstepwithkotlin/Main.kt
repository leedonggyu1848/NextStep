package com.example.nextstepwithkotlin

import org.apache.logging.log4j.kotlin.logger
import java.net.ServerSocket


private val log = logger("main")
private val DEFAULT_PORT: Int = 8080

fun main(args: Array<String>) {
    val port = if (args.isEmpty()) DEFAULT_PORT else Integer.parseInt(args[0])

    ServerSocket(port).use { server ->
        log.info { "Web Application Server started $port port" }
        generateSequence { server.accept() }
                .takeWhile{ it != null }
                .forEach { RequestHandler(it).start() }
    }
}