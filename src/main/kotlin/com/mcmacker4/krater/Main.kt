package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router("localhost") {

            get("/") {
                contentType("text/html")
                body = "<h1>Hello World</h1>"
            }

        }

    }

    Server(app).start()

}