package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {
        
        router {

            get("/") { req ->
                contentType("application/json")
                body = """
                    {
                        "message": "${req.body}"
                    }
                """.trimIndent()
            }

        }

    }

    Server(app).start()

}