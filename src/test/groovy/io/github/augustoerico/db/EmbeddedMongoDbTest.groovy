package io.github.augustoerico.db

import com.mongodb.async.client.MongoClient
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.IMongodConfig
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class EmbeddedMongoDbTest extends Specification {

    @Shared
    Repository repository

    @Shared
    MongodExecutable executable
    @Shared
    Vertx vertx

    def setup() {

        vertx = Vertx.vertx()
        // ------------------------------------------------------
        MongodStarter starter = MongodStarter.getDefaultInstance()

        String bindIp = 'localhost'
        int port = 12345

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build()

        executable = starter.prepare(mongodConfig)
        executable.start()

        // ------------------------------------------------------

        repository = Repository.create(vertx).getInstance()
    }

    def cleanup() {
        executable.stop()
        vertx.close()
    }

    def 'It should insert data into base'() {
        def async = new AsyncConditions()

        given:
        def data = new JsonObject([name: 'Erico'])

        when:
        repository.save('test', data) {
            async.evaluate { true }
        }

        then:
        async.await(5.0)

        when:
        async = new AsyncConditions()

        and:
        repository.find('test') { Future future ->
            if (future.succeeded()) {
                println 'AEHOOOOOOOO'
                def result = future.result()
                println result
                if (result.size() == 1) {
                    async.evaluate { true }
                }
            } else {
                future.cause().printStackTrace()
            }
        }

        then:
        async.await(5.0)

    }

}
