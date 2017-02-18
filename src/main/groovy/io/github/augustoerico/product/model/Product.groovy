package io.github.augustoerico.product.model

import io.vertx.core.json.JsonObject

class Product {

    def id
    def name
    def price

    def asJson() {
        new JsonObject()
                .put('id', id)
                .put('name', name)
                .put('price', price as double)
    }
}
