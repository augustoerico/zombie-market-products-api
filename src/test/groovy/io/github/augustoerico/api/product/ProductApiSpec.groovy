package io.github.augustoerico.api.product

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.TestHelper
import io.github.augustoerico.api.Loader
import io.github.augustoerico.api.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.product.model.Product
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification

abstract class ProductApiSpec extends Specification {

    Vertx vertx
    MongodExecutable executable

    @Shared
    RESTClient client
    @Shared
    Repository repository

    def setup() {
        vertx = Vertx.vertx()

        TestHelper.setupServer(vertx)

        executable = TestHelper.getMongodExecutable()
        executable.start()

        repository = Repository.create(vertx).getInstance()

        client = new RESTClient('http://localhost:3000')
        client.setContentType('application/json')

        setupContext()
    }

    def cleanup() {
        executable.stop()
        vertx.close()
    }

    def load() {
        def products = Fixture.PRODUCTS.collect {
            (new Product(it)).asJson()
        }
        Loader.create(vertx)
                .withCollection(Env.productsCollection())
                .withItems(products)
                .load()
    }

    abstract setupContext()

}
