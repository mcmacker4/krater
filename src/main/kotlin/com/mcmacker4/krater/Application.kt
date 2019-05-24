package com.mcmacker4.krater


class Application(builder: Builder.() -> Unit) {

    val routers = arrayListOf<Router>()

    init { Builder(this).apply(builder) }


    fun handleRequest(request: Request) : Response {
        val router = routers[0]
        val route = router.routes[0]
        
        val response = Response()
        route.handler(response, request)
        
        return response
    }
    
    
    class Builder(private val app: Application) {

        fun router(block: Router.Builder.() -> Unit) {
            app.routers.add(Router(block))
        }

    }

}
