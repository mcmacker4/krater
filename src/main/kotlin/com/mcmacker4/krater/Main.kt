package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router {

            get("/") { req ->
                contentType(req.contentType() ?: "text/plain")
                body = req.body
            }

        }

    }

    Server(app).start()

}