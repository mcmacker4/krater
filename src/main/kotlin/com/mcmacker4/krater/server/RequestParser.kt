package com.mcmacker4.krater.server

import com.mcmacker4.krater.Headers
import com.mcmacker4.krater.Request
import java.io.BufferedReader
import java.lang.Exception


class RequestParser {

    fun parseRequest(reader: BufferedReader) : Request {
        try {
            
            val requestLine = reader.readLine().split(" ").map { it.trim() }
            
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
    
            return Request(requestLine[0], requestLine[1], requestLine[2], headers, body)
            
        } catch(ex: Exception) {
            throw Exception("Error parsing request.", ex)
        }
    }

}