package io.github.augustoerico.api

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.product.model.Product
import io.vertx.core.Future
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.TimeUnit

class CreateProductTest extends Specification {

    @Shared
    Vertx vertx

    @Shared
    MongodExecutable executable

    @Shared
    RESTClient client

    @Shared
    Repository repository

    def setup() {
        vertx = Vertx.vertx()

        ProductTestHelper.setupServer(vertx)

        executable = ProductTestHelper.getMongodExecutable()
        executable.start()

        repository = Repository.create(vertx).getInstance()

        client = new RESTClient('http://localhost:3000')
        client.setContentType('application/json')
    }

    def cleanup() {
        executable.stop()
        vertx.close()
    }

    def 'It should create a product'() {
        def vars = new BlockingVariables(Env.testWaitTime() as int, TimeUnit.SECONDS)

        given:
        def body = new Product([name: 'Right hand', price: 7.99])

        when:
        def response = client.post path: '/products', body: body

        then:
        response.status == 201
        response.responseData.name == body.name
        response.responseData.price == body.price

        when: 'query data'
        String id = response.responseData._id
        repository.findOne(Env.productsCollection(), id) { Future future ->
            if (future.succeeded()) {
                vars.data = future.result().map
            }
        }

        then:
        vars.data == response.responseData
    }

}
