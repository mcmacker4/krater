package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router("localhost") {

            get("/") {
                contentType("text/html")
                val name = it.query?.get("name") ?: "World"
                body = "<h1>Hello $name</h1>"
            }

        }

        router("192.168.1.11") {

            get("/") {
                contentType("text/html")
                body = "<h1>Hello From the Other Side</h1>"
            }

        }

    }

    Server(app).start()

}