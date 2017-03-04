package io.github.augustoerico.api.product

import io.github.augustoerico.api.fixture.Fixture

class GetAllProductApiSpec extends ProductApiSpec {

    @Override
    setupContext() {
        load()
    }

    def 'Should get all products'() {
        when:
        def response = client.get path: '/products'

        then:
        response.status == 200
        response.responseData.size() == 3
        response.responseData.get(0) == Fixture.PRODUCTS[0]
        response.responseData.get(1) == Fixture.PRODUCTS[1]
        response.responseData.get(2) == Fixture.PRODUCTS[2]

    }

}
