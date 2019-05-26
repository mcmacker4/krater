package com.mcmacker4.krater

typealias RequestHandler = Response.(Request) -> Unit

typealias Headers = HashMap<String, String>
typealias Cookies = HashMap<String, String>
typealias Query = HashMap<String, String>