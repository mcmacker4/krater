package com.mcmacker4.krater


class Response {

    var status = 200
    val headers = Headers()
    var body: Any? = null

    fun redirect(url: String) {
        headers["Location"] = url
        status = 301
    }

}