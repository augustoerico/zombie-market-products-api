package io.github.augustoerico.db

import io.github.augustoerico.ServerVerticle
import io.github.augustoerico.config.Env
import io.github.augustoerico.product.model.Product
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class RepositoryTest extends Specification {

    @Shared
    Repository repository

    def setupSpec() {
        def async = new AsyncConditions()

        def vertx = Vertx.vertx()
        vertx.deployVerticle(ServerVerticle.name) {
            repository = Repository.create(vertx).instance
            async.evaluate { true }
        }
        async.await(Env.testWaitTime())
    }

    def 'Should save a product'() {
        def async = new AsyncConditions()

        given:
        def product = new Product([name: 'Hand', price: 4.99])

        when:
        repository.save('PRODUCT', product.asJson()) {
            async.evaluate { true }
        }

        then:
        async.await Env.testWaitTime()
    }

    def 'Should find all products'() {
        def async = new AsyncConditions()

        when:
        repository.find('PRODUCT') {
            async.evaluate { true }
        }

        then:
        async.await Env.testWaitTime()
    }

}
