package io.github.augustoerico.routes

import io.github.augustoerico.product.handlers.CreateProductHandler
import io.github.augustoerico.product.handlers.GetAllProductHandler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router

class ProductRouter {

    Router router

    ProductRouter(Router router) {
        this.router = router
    }

    def route() {
        router.route(HttpMethod.GET, '/products').handler GetAllProductHandler.handler
        router.route(HttpMethod.POST, '/products').handler CreateProductHandler.handler
    }

    static create(Router router) {
        new ProductRouter(router)
    }
}
