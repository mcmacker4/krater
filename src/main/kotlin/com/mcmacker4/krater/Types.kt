package com.mcmacker4.krater

typealias RequestHandler = Response.(Request) -> Unit
typealias Headers = HashMap<String, String>

data class Route(val path: String, val handler: RequestHandler)