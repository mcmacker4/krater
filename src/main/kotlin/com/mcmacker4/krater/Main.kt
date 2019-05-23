package com.mcmacker4.krater

import com.mcmacker4.krater.server.Server

fun main() {

    val app = Application {

        route("/") {
            body = "Hello World!"
            headers["Content-Type"] = "text/html"
        }

        route("/es") {
            redirect("/")
        }

    }

    Server(app).start()

}