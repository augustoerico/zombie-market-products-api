package io.github.augustoerico.product.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.product.model.Product
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext

class CreateProductHandler {

    static handler = { RoutingContext context ->
        println '[POST] on /products'

        def response = context.response()
        def body = context.getBodyAsJson().map
        def product = new Product(body)
        Repository.create(context.vertx())
                .save(Env.productsCollection(), product.asJson(), handleResult.curry(response, product))
    }

    static handleResult = { HttpServerResponse response, Product product, Future future ->

        response.putHeader('Access-Control-Allow-Origin', '*')
        if (future.succeeded()) {
            product.set_id(future.result())
            response.setStatusCode(201).end(product.asJson().encodePrettily())
        } else {
            def cause = future.cause()
            cause.printStackTrace()
            response.setStatusCode(500).end(cause.message)
        }

    }
}
