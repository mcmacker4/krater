package com.mcmacker4.krater.server

import com.mcmacker4.krater.Application
import com.mcmacker4.krater.Response
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
            val request = parser.parseRequest(istream.reader())
            val response = app.handleRequest(request)
            println("${request.path} -> ${response.status}")
            response.write(ostream.writer())
            socket.close()
        } catch (ex: Exception) {
            internalServerError(ex)
            ex.printStackTrace()
        }
    }
    
    private fun internalServerError(ex: Exception) {
        Response().apply {
            status = 500
        }.write(ostream.writer())
    }

}