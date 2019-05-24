package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router {

            get("/") {
                contentType("application/json")
                body = """
                    {
                        "message": "Hello World"
                    }
                """.trimIndent()
            }

        }

    }

    Server(app).start()

}