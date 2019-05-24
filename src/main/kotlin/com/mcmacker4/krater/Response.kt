package com.mcmacker4.krater

import java.io.OutputStreamWriter

class Response {

    var status = 200
    val headers = Headers()
    var body: Any? = null

    fun redirect(url: String) {
        headers["Location"] = url
        status = 301
    }
    
    internal fun write(ostream: OutputStreamWriter) {
        ostream.write("HTTP/1.1 200 OK\r\n")
        for ((name, value) in headers) {
            ostream.write("$name: $value\r\n")
        }
        ostream.write("\r\n")
        ostream.write(body.toString())
        ostream.flush()
    }

}