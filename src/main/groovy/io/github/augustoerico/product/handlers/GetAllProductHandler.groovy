package io.github.augustoerico.product.handlers

import io.github.augustoerico.db.Repository
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext

class GetAllProductHandler {

    static final String PRODUCT_COLLECTION = 'PRODUCT'

    static handler = { RoutingContext context ->
        println '[GET] on /products'

        def response = context.response()
        Repository.create(context.vertx())
                .find(PRODUCT_COLLECTION, handleResult.curry(response))
    }

    static handleResult = { HttpServerResponse response, Future future ->

        if (future.succeeded()) {
            def result = future.result()
            response.putHeader('content-type', 'application/json')
                    .end(result.toString())
        } else {
            response.setStatusCode(500).end()
        }

    }

}
