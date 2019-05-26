package com.mcmacker4.krater.server

import com.mcmacker4.krater.Cookies
import com.mcmacker4.krater.Headers
import com.mcmacker4.krater.Query
import com.mcmacker4.krater.Request
import com.mcmacker4.krater.server.exceptions.BadRequestException
import com.mcmacker4.krater.server.exceptions.InvalidRequestMethodException
import java.io.BufferedReader
import java.lang.Exception


class RequestParser {

    fun parseRequest(reader: BufferedReader) : Request {
        try {
            
            val requestLine = reader.readLine().split(" ").map { it.trim() }
            
            val method = try {
                RequestMethod.valueOf(requestLine[0])
            } catch (ex: IllegalArgumentException) {
                throw InvalidRequestMethodException(requestLine[0], ex)
            }
            
            val headers = Headers()
            var line = reader.readLine()
            while (line.isNotBlank()) {
                val parts = line.split(":")
                headers[parts[0].trim()] = parts[1].trim()
                line = reader.readLine()
            }
            
            val contentLength = headers["Content-Length"]?.toInt()
            val body = if (contentLength != null) {
                val buffer = CharArray(contentLength)
                reader.read(buffer, 0, contentLength)
                String(buffer)
            } else null
            
            val cookies = parseCookies(headers["Cookie"])

            val pathParts = requestLine[1].split("?").toList()
            val queryString = pathParts.getOrNull(1)
            val query = parseQuery(queryString)
    
            return Request(method, pathParts[0], requestLine[2], headers, query, cookies, body)
            
        } catch(ex: Exception) {
            throw BadRequestException("Error parsing request.", ex)
        }
    }

    private fun parseCookies(header: String?) : Cookies? {
        if (header == null || header.isEmpty()) return null
        val pairs = header.split(";").map {
            val parts = it.trim().split("=")
            Pair(parts[0].trim(), parts[1].trim())
        }.toTypedArray()
        return hashMapOf(*pairs)
    }

    private fun parseQuery(queryString: String?) : Query? {
        if (queryString == null || queryString.isEmpty()) return null
        val pairs = queryString.split("&").map {
            val parts = it.split("=")
            Pair(parts[0], parts.getOrElse(1) { "" })
        }.toTypedArray()
        return hashMapOf(*pairs)
    }

}