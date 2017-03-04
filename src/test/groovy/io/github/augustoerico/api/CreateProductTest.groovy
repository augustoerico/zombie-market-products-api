package io.github.augustoerico.api

import io.github.augustoerico.config.Env
import io.github.augustoerico.product.model.Product
import io.vertx.core.Future
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.TimeUnit

class CreateProductTest extends ProductTest {

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
