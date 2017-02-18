package io.github.augustoerico

import io.github.augustoerico.config.Env
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class ServerVerticle extends AbstractVerticle {

    @Override
    void start(Future future) {
        Router router = Router.router(vertx)

        // TODO move this to somewhere else
        router.route(HttpMethod.GET, '/').handler { RoutingContext context ->
            def response = context.response()
            response.putHeader('content-type', 'text/html')
                    .end('<h1>Hello, world! Welcome to vertx!</h1>')
        }

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
