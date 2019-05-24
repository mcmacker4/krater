package com.mcmacker4.krater


data class Endpoint(val path: String, val method: RequestMethod, val handler: RequestHandler)


class Router(block: Builder.() -> Unit) {
    
    val routes = arrayListOf<Endpoint>()
    
    init { Builder(this).apply(block) }

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
    
}