package com.example.nextstepwithkotlin

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.catalina.WebResourceRoot
import org.apache.catalina.startup.Tomcat
import org.apache.catalina.webresources.DirResourceSet
import org.apache.catalina.webresources.StandardRoot
import java.io.File
import java.io.IOException


private val log = KotlinLogging.logger {}

object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val tomcat = Tomcat()
        tomcat.setPort(8080)
        tomcat.connector

        val contextPath = ""
        val docBase = File("src/main/webapp").absolutePath

        log.trace { "This is trace log" }
        log.debug { "This is debug log" }
        log.info { "This is info log" }
        log.warn { "This is warn log" }
        log.error { "This is error log" }

        val context = tomcat.addWebapp(contextPath, docBase)

        class SampleServlet : HttpServlet() {
            @Throws(ServletException::class, IOException::class)
            override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
                val writer = resp.writer

                writer.println("<html><title>Welcome to servlet</title><body>")
                writer.println("<h1>This is a Servlet</h1>")
                writer.println("</body></html>")
            }
        }

        val servletName = "SampleServlet"
        val urlPattern = "/servlet"

        tomcat.addServlet(contextPath, servletName, SampleServlet())
        context.addServletMappingDecoded(urlPattern, servletName)

        val additionWebInfClasses = File("build/classes")
        val resources: WebResourceRoot = StandardRoot(context)
        resources.addPreResources(
            DirResourceSet(
                resources,
                "/WEB-INF/classes",
                additionWebInfClasses.absolutePath,
                "/"
            )
        )
        context.resources = resources
        tomcat.start()
        tomcat.server.await()
    }
}
