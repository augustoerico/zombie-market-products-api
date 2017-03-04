package io.github.augustoerico.api.product

import io.github.augustoerico.api.fixture.Fixture

class GetProductApiSpec extends ProductApiSpec {

    @Override
    setupContext() {
        load()
    }

    def 'Should get a product by ID'() {

        given:
        def id = '2345'

        when:
        def response = client.get path: "/products/$id"

        then:
        response.status == 200
        response.responseData == Fixture.PRODUCTS.get(1)

    }

}
