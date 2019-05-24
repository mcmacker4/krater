package com.mcmacker4.krater

import com.mcmacker4.krater.server.Status
import java.io.OutputStreamWriter

class Response {

    var status = Status.OK
    val headers = Headers()
    var body: Any? = null

    fun redirect(url: String) {
        headers["Location"] = url
        status = Status.MovedPermanently
    }
    
    internal fun write(ostream: OutputStreamWriter) {
        ostream.write("HTTP/1.1 ${status.code} ${status.string}\r\n")
        for ((name, value) in headers) {
            ostream.write("$name: $value\r\n")
        }
        ostream.write("\r\n")
        if (body != null) {
            ostream.write(body.toString())
        }
        ostream.flush()
    }

    fun contentType(type: String) {
        headers["Content-Type"] = type
    }

}