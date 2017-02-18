package io.github.augustoerico

import io.github.augustoerico.config.Env
import io.github.augustoerico.routes.HealthRouter
import io.github.augustoerico.routes.ProductRouter
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router

class ServerVerticle extends AbstractVerticle {

    @Override
    void start(Future future) {
        Router router = Router.router(vertx)

        HealthRouter.create(router).route()
        ProductRouter.create(router).route()

        vertx.createHttpServer()
                .requestHandler(router.&accept)
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
