package io.github.augustoerico.api

import io.github.augustoerico.api.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.product.model.Product
import spock.util.concurrent.BlockingVariables

import java.util.concurrent.TimeUnit

class GetAllProductTest extends ProductTest {

    def loadData(products, Closure handler) {
        if (products) {
            def product = new Product(products.head())
            repository.client.insert(Env.productsCollection(), product.asJson()) {
                loadData(products.tail(), handler)
            }
        } else {
            handler()
        }
    }

    def 'Should get all products'() {
        def vars = new BlockingVariables(20, TimeUnit.SECONDS)

        when:
        loadData(Fixture.GET_ALL_TEST_DATA) {
            vars.dataLoaded = true
        }

        then: 'data is loaded'
        vars.dataLoaded

        when:
        def response = client.get path: '/products'

        then:
        response.status == 200
        response.responseData.size() == 3
        response.responseData.get(0) == Fixture.GET_ALL_TEST_DATA[0]
        response.responseData.get(1) == Fixture.GET_ALL_TEST_DATA[1]
        response.responseData.get(2) == Fixture.GET_ALL_TEST_DATA[2]

    }

}
