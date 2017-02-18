package io.github.augustoerico

import io.github.augustoerico.config.Env
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class ServerVerticle extends AbstractVerticle {

    @Override
    void start(Future future) {
        vertx.createHttpServer()
                .requestHandler { req -> req.response().end('Hello, world') }
                .listen(Env.port(), Env.address(), handleResult.curry(future))
    }

    def handleResult = { Future future, result ->
        if (result.succeeded()) {
            println "Server running on http://${Env.address()}:${Env.port()}"
            future.complete()
        } else {
            def ex = result.cause()
            ex.printStackTrace()
            future.fail(ex)
        }
    }

}
