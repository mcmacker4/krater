package com.mcmacker4.krater

import com.mcmacker4.krater.server.RequestMethod
import java.lang.StringBuilder
import java.util.regex.Pattern


class Request(
    val method: RequestMethod,
    val path: String,
    val version: String,
    val headers: Headers,
    val body: String?
) {
    
    val cookies = parseCookies()
    
    fun contentType() = headers["Content-Type"]
    fun host() = headers["Host"]
    
    private fun parseCookies() : Cookies {
        val header = headers["Cookie"] ?: return hashMapOf()
        val pairs = header.split(Pattern.compile("\\s*;\\s*")).map {
            val parts = it.split(Pattern.compile("\\s*=\\s*"))
            Pair(parts[0], parts[1])
        }.toTypedArray()
        return hashMapOf(*pairs)
    }

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