package com.mcmacker4.krater.server

import com.mcmacker4.krater.Headers
import com.mcmacker4.krater.Request
import java.io.InputStreamReader
import java.lang.StringBuilder


class RequestParser {

    fun parseRequest(istream: InputStreamReader) : Request {
        val text = StringBuilder()
        val buffer = CharArray(512)
        
        var read = istream.read(buffer)
        while (read != -1) {
            text.append(buffer, 0, read)
            if (isValidRequest(text))
                break
            read = istream.read(buffer)
        }

        val headBody = text.split("\r\n\r\n")
        val head = headBody[0]
        val body = headBody[1]

        val splitHead = head.split("\r\n")
        val requestLine = splitHead[0]

        val splitRequestLine = requestLine.split(" ")
        val method = splitRequestLine[0]
        val path = splitRequestLine[1]
        val version = splitRequestLine[2]

        val headers = Headers()

        if (splitHead.size > 1) {
            for (i in 1 until splitHead.size) {
                val splitHeader = splitHead[i].split(":")
                headers[splitHeader[0].trim()] = splitHeader[1].trim()
            }
        }
        
        return Request(method, path, version, headers, body)
    }
    
    private fun isValidRequest(text: CharSequence) : Boolean {
        val gotHeaders = hasEndOfHeaderSection(text)
        val expectBody = expectBody(text)
        
        return gotHeaders && !expectBody
    }
    
    private fun hasEndOfHeaderSection(text: CharSequence) : Boolean {
        return text.contains("\r\n\r\n")
    }
    
    private fun expectBody(text: CharSequence) : Boolean {
        return false
    }

}