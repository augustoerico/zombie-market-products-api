package io.github.augustoerico.product.model

import io.vertx.core.json.JsonObject

class Product {

    def _id
    def name
    def price

    Product(Map map) {
        this.name = map.name
        this.price = map.price
    }

    def asJson() {
        def obj = new JsonObject()
        _id ? obj.put('_id', _id) : null
        name ? obj.put('name', name) : null
        price ? obj.put('price', price as double) : null
        obj
    }
}
