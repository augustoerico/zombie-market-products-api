package io.github.augustoerico.db

import groovy.json.JsonOutput
import io.github.augustoerico.config.Env
import io.github.augustoerico.product.model.Product
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient

class ProductRepository {

    static final String PRODUCT_COLLECTION = 'PRODUCT'
    static ProductRepository instance

    MongoClient client

    private ProductRepository(MongoClient client) {
        this.client = client
    }


    def save(Product product, Handler handler) {
        client.save(PRODUCT_COLLECTION, product.asJson(), handler)
    }

    static create(Vertx vertx) {
        if (instance) {
            throw Exception('ProductRepository already has an instance')
        }

        def json = new JsonObject()
                .put('connection_string', Env.mongoDbUri())
                .put('db_name', Env.mongoDbName())

        println json

        def client = MongoClient.createShared(vertx, json)

        println client.toString()

        instance = new ProductRepository(client)
    }
    static instance() {
        if (!instance) {
            throw Exception('ProductRepository not initialized')
        }

        instance
    }

}
