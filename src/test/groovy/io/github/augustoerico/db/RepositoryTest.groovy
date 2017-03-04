package io.github.augustoerico.db

import de.flapdoodle.embed.mongo.MongodExecutable
import io.github.augustoerico.TestHelper
import io.github.augustoerico.config.Env
import io.github.augustoerico.product.model.Product
import io.vertx.core.Future
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class RepositoryTest extends Specification {

    /**
     * TODO: this integration is incomplete. It should use de MongoClient to load and read data.
     */

    Vertx vertx
    MongodExecutable executable

    @Shared
    Repository repository

    def setup() {
        vertx = Vertx.vertx()
        repository = Repository.create(vertx).getInstance()

        executable = TestHelper.getMongodExecutable()
        executable.start()
    }

    def cleanup() {
        executable.stop()
        vertx.close()
    }

    def 'Should save a product'() {
        def async = new AsyncConditions()

        given:
        def product = new Product([name: 'Hand', price: 4.99])

        when:
        repository.save(Env.productsCollection(), product.asJson()) {
            async.evaluate { true }
        }

        then:
        async.await Env.testWaitTime()
    }

    def 'Should find all products'() {
        def async = new AsyncConditions()

        when:
        repository.find(Env.productsCollection()) { Future future ->
            async.evaluate { true }
        }

        then:
        async.await Env.testWaitTime()
    }

}
