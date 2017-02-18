package io.github.augustoerico.product.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext

class GetProductHandler {

    static handler = { RoutingContext context ->
        println '[GET] on /products'

        def response = context.response()
        def id = context.request().getParam('id')
        Repository.create(context.vertx())
                .findOne(Env.productsCollection(), id, handleResult.curry(response))

    }

    static handleResult = { HttpServerResponse response, Future future ->

        if (future.succeeded()) {
            def result = future.result()
            result ?
                    response.end(result.toString()) :
                    response.setStatusCode(404).end()
        } else {
            future.cause().printStackTrace()
            response.setStatusCode(500).end()
        }

    }

}
