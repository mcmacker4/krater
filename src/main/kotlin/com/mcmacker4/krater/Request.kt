package com.mcmacker4.krater

import com.mcmacker4.krater.server.RequestMethod
import java.lang.StringBuilder
import java.util.regex.Pattern


@Suppress("MemberVisibilityCanBePrivate")
class Request(
    val method: RequestMethod,
    val path: String,
    val version: String,
    val headers: Headers,
    val query: Query?,
    val cookies: Cookies?,
    val body: String?
) {

    val host get() = headers["Host"]
    val contentType get() = headers["Content-Type"]

    override fun toString(): String {
        val text = StringBuilder()
        text.appendln("$method $path $version")
        for ((name, value) in headers) {
            text.appendln("$name: $value")
        }
        text.appendln()
        if(body != null)
            text.append(body)
        return text.toString()
    }
    
}