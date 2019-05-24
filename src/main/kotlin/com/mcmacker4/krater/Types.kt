package com.mcmacker4.krater

typealias RequestHandler = Response.(Request) -> Unit
typealias Headers = HashMap<String, String>


enum class RequestMethod {
    GET,
    POST,
    HEAD
}