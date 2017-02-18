package io.github.augustoerico.product.handlers

import io.vertx.ext.web.RoutingContext

class GetProductHandler {

    static handler = { RoutingContext context ->
        def response = context.response()

        def id = context.request().getParam('id')

    }

}
