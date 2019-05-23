package com.mcmacker4.krater


class Application(builder: Builder.() -> Unit) {

    private val routes = hashMapOf<String, Route>()

    init { Builder(this).apply(builder) }

    class Builder(private val app: Application) {

        fun route(path: String, handler: RequestHandler) {
            if (app.routes.containsKey(path))
                println("[WARN] Path $path already defined. Replacing.")
            app.routes[path] = Route(path, handler)
        }

    }

}
