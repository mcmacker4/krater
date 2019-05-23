package com.mcmacker4.krater.server

import com.mcmacker4.krater.Application
import java.net.ServerSocket
import java.util.concurrent.Executors


class Server(
    private val app: Application,
    private val port: Int = 8080
) : Thread() {

    private val serverSocket = ServerSocket(port)
    private val threadPool = Executors.newCachedThreadPool()

    override fun run() {
        println("Listening on port $port\n")
        while (true) {
            val socket = serverSocket.accept()
            threadPool.submit(RequestExecutor(app, socket))
        }
    }

}