package com.mcmacker4.krater


class Application(builder: Builder.() -> Unit) {

    val routers = arrayListOf<Router>()

    init { Builder(this).apply(builder) }


    fun handleRequest(request: Request) : Response? {
        val host = request.host
        val router = routers.find { it.host == host }
        return router?.handleRequest(request)
    }
    
    
    class Builder(private val app: Application) {

        fun router(host: String = "*", block: Router.Builder.() -> Unit) {
            app.routers.add(Router(host, block))
        }

    }

}
