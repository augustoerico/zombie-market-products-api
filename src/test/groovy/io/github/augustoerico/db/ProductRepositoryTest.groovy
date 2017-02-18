package io.github.augustoerico.db

import io.github.augustoerico.ServerVerticle
import io.github.augustoerico.product.model.Product
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner.class)
class ProductRepositoryTest {

    Vertx vertx

    @Before
    void setUp(TestContext context) {
        vertx = Vertx.vertx()
        vertx.deployVerticle(ServerVerticle.name) {
            context.asyncAssertSuccess()
        }
    }
//
//    @After
//    void tearDown(TestContext context) {
//        vertx.close context.asyncAssertSuccess()
//    }

    @Test
    void testRepositorySave(TestContext context) {
        final async = context.async()

        Product product = new Product([id: '1234', name: 'name', price: 10.5])
        ProductRepository repository = ProductRepository.create(vertx).instance()
        repository.save(product) { Future future ->
            if (future.succeeded()) {
                println 'Future complete'
                async.complete()
            } else {
                println 'Error'
                def ex = future.cause()
                ex.printStackTrace()
            }
        }
    }

}
