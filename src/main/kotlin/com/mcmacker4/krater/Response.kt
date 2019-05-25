package com.mcmacker4.krater

import com.mcmacker4.krater.server.Status
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

class Response() {

    var status = Status.OK
    val headers = Headers()
    var body: Any? = null
    
    private val cookies = Cookies()
    
    constructor(status: Status) : this() {
        this.status = status
    }

    fun contentType(type: String) {
        headers["Content-Type"] = type
    }

    fun redirect(url: String) {
        headers["Location"] = url
        status = Status.MovedPermanently
    }
    
    fun setCookie(name: String, value: String) {
        cookies[name] = value
    }
    
    fun deleteCookie(name: String) {
        cookies[name] = ""
    }
    
    private fun cookieHeader() : String {
        val value = cookies.map { "${it.key}=${it.value}" }.joinToString("; ")
        return "Set-Cookie: $value\r\n"
    }
    
    internal fun write(ostream: OutputStreamWriter) {
        ostream.write("HTTP/1.1 ${status.code} ${status.string}\r\n")
        
        if (cookies.size > 0)
            ostream.write(cookieHeader())
        
        for ((name, value) in headers)
            ostream.write("$name: $value\r\n")
        
        ostream.write("\r\n")
        
        if (body != null)
            ostream.write(body.toString())
        
        ostream.flush()
    }

    override fun toString(): String {
        val ostream = ByteArrayOutputStream()
        write(ostream.writer())
        return ostream.toString()
    }

}