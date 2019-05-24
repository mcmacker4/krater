package com.mcmacker4.krater

import com.mcmacker4.krater.server.RequestMethod


class Request(
    val method: RequestMethod,
    val path: String,
    val version: String,
    val headers: Headers,
    val body: String?
) {
    
    fun contentType() = headers["Content-Type"]
    
}