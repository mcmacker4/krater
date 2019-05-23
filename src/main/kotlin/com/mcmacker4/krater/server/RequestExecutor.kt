package com.mcmacker4.krater.server

import com.mcmacker4.krater.Application
import java.net.Socket


class RequestExecutor(
    private val app: Application,
    private val socket: Socket
) : Runnable {

    val istream = socket.getInputStream()
    val ostream = socket.getOutputStream()

    val parser = RequestParser()

    override fun run() {

        socket.close()
    }

}