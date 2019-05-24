package com.mcmacker4.krater.server

import com.mcmacker4.krater.Application
import com.mcmacker4.krater.Response
import com.mcmacker4.krater.server.exceptions.BadRequestException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket


class RequestExecutor(
    private val app: Application,
    private val socket: Socket
) : Runnable {

    private val istream: InputStream = socket.getInputStream()
    private val ostream: OutputStream = socket.getOutputStream()

    private val parser = RequestParser()

    override fun run() {
        try {
            val request = parser.parseRequest(istream.bufferedReader())
            val response = app.handleRequest(request)
            println("${request.path} -> ${response.status.string}")
            response.write(ostream.writer())
        } catch (ex: BadRequestException) {
            respondError(Status.BadRequest, ex.message)
        } catch (ex: Exception) {
            respondError(Status.InternalServerError, ex.message)
        } finally {
            socket.close()
        }
    }
    
    private fun respondError(status: Status, body: Any?) {
        Response().apply {
            this.status = status
            this.body = body
        }.write(ostream.writer())
    }

}