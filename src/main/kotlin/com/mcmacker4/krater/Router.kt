package com.mcmacker4.krater

import com.mcmacker4.krater.server.RequestMethod


class Router(val host: String, block: Builder.() -> Unit) {

    val routes = arrayListOf<Endpoint>()
    
    init { Builder(this).apply(block) }
    
    fun handleRequest(request: Request) : Response? {
        val endpoint = routes.firstOrNull { it.method == request.method && it.path == request.path }
        if (endpoint != null) {
            val response = Response()
            endpoint.handler(response, request)
            return response
        }
        return null
    }

    class Builder(private val router: Router) {
        
        fun get(path: String, handler: RequestHandler) {
            router.routes.add(Endpoint(path, RequestMethod.GET, handler))
        }
        
        fun post(path: String, handler: RequestHandler) {
            router.routes.add(Endpoint(path, RequestMethod.POST, handler))
        }
        
        fun head(path: String, handler: RequestHandler) {
            router.routes.add(Endpoint(path, RequestMethod.HEAD, handler))
        }
        
    }

    data class Endpoint(val path: String, val method: RequestMethod, val handler: RequestHandler)
    
}