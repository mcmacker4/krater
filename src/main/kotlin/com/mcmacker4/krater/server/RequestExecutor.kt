package com.mcmacker4.krater.server

import com.mcmacker4.krater.Application
import com.mcmacker4.krater.Response
import com.mcmacker4.krater.server.exceptions.BadRequestException
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.Socket


class RequestExecutor(
    private val app: Application,
    private val socket: Socket
) : Runnable {

    private val istream: BufferedReader = socket.getInputStream().bufferedReader()
    private val ostream: OutputStreamWriter = socket.getOutputStream().writer()

    private val parser = RequestParser()

    override fun run() {
        try {
            val request = parser.parseRequest(istream)
            val response = app.handleRequest(request) ?: Response(Status.NotFound)
            println("${request.method} ${request.path} -> ${response.status.string}")
            response.write(ostream)
        } catch (ex: BadRequestException) {
            ex.printStackTrace()
            respondError(Status.BadRequest, ex.message)
        } catch (ex: Exception) {
            ex.printStackTrace()
            respondError(Status.InternalServerError, ex.message)
        } finally {
            socket.close()
        }
    }
    
    private fun respondError(status: Status, body: Any?) {
        Response().apply {
            this.status = status
            this.body = body
        }.write(ostream)
    }

}