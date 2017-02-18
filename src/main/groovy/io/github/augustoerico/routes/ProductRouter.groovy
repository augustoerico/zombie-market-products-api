package io.github.augustoerico.routes

import io.github.augustoerico.product.handlers.CreateProductHandler
import io.github.augustoerico.product.handlers.GetAllProductHandler
import io.github.augustoerico.product.handlers.GetProductHandler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class ProductRouter {

    Router router

    ProductRouter(Router router) {
        this.router = router
    }

    def route() {
        router.route().handler(BodyHandler.create())
        router.get('/products').handler GetAllProductHandler.handler
        router.get('/products/:id').handler GetProductHandler.handler
        router.post('/products').handler CreateProductHandler.handler
    }

    static create(Router router) {
        new ProductRouter(router)
    }
}
