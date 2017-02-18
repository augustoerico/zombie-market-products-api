package io.github.augustoerico.product.handlers

import io.vertx.ext.web.RoutingContext

class CreateProductHandler {

    static handler = { RoutingContext context ->
        def response = context.response()
        def body = context.getBodyAsJson()
    }

}
