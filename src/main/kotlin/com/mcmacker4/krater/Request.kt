package com.mcmacker4.krater

import com.mcmacker4.krater.server.RequestMethod
import java.lang.StringBuilder
import java.util.regex.Pattern


class Request(
    val method: RequestMethod,
    val path: String,
    val version: String,
    val headers: Headers,
    val queryString: String?,
    val body: String?
) {
    
    val cookies = parseCookies()
    val query = parseQuery()
    
    fun contentType() = headers["Content-Type"]
    fun host() = headers["Host"]
    
    private fun parseCookies() : Cookies? {
        val header = headers["Cookie"] ?: return null
        val pairs = header.split(Pattern.compile("\\s*;\\s*")).map {
            val parts = it.split(Pattern.compile("\\s*=\\s*"))
            Pair(parts[0], parts[1])
        }.toTypedArray()
        return hashMapOf(*pairs)
    }
    
    private fun parseQuery() : Query? {
        if (queryString == null) return null
        val pairs = queryString.split("&").map {
            val parts = it.split("=")
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