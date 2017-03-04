package io.github.augustoerico.api

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.db.Repository
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification

class ProductTest extends Specification {

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

}
