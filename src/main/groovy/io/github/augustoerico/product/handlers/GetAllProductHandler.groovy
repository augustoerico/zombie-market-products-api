package io.github.augustoerico.product.handlers

import groovy.json.JsonOutput
import io.vertx.ext.web.RoutingContext

class GetAllProductHandler {

    // TODO remove this kebab
    static products = [
            [id: 1, description: 'A generic item', price: 1.0],
            [id: 2, description: 'Kebab', price: 2.89],
            [id: 3, description: 'Another one', price: 10.9]
    ]

    static handler = { RoutingContext context ->
        def response = context.response()
        response.putHeader('content-type', 'application/json')
                .end(JsonOutput.toJson(products))
    }

}
