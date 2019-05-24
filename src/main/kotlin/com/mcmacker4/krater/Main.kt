package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router {

            get("/") {
                body = "<h1>Hello World!</h1>"
                headers["Content-Type"] = "text/html"
            }

        }

    }

    Server(app).start()

}