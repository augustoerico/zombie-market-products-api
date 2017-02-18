package io.github.augustoerico.routes

import io.github.augustoerico.products.GetAllProductHandler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router

class ProductRouter {

    Router router

    ProductRouter(Router router) {
        this.router = router
    }

    def route() {
        router.route(HttpMethod.GET, '/products').handler GetAllProductHandler.handler
    }

    static create(Router router) {
        new ProductRouter(router)
    }
}
