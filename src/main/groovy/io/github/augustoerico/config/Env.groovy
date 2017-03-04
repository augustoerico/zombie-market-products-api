package io.github.augustoerico.config

class Env {

    static port() {
        def port = System.getenv().PORT ?: '3000'
        Integer.parseInt(port)
    }

    static host() {
        System.getenv().HOST ?: '127.0.0.1'
    }

    static appUrl() {
        System.getenv().APP_URL ?: "http://${host()}:${port()}"
    }

    /**
     * Mongo DB
     */
    static mongoDbHost() {
        System.getenv().MONGO_DB_HOST ?: host()
    }

    static mongoDbPort() {
        def port = System.getenv().MONGO_DB_PORT ?: '12345'
        Integer.parseInt(port)
    }

    static mongoDbUri() {
        System.getenv().MONGO_DB_URI ?: "mongodb://${host()}:${mongoDbPort()}"
    }

    static mongoDbName() {
        System.getenv().MONGO_DB_NAME ?: 'db-name'
    }

    static productsCollection() {
        System.getenv().PRODUCTS_COLLECTION ?: 'PRODUCTS'
    }

    /**
     * Tests
     */
    static Double testWaitTime() {
        def time = System.getenv().TEST_WAIT_TIME ?: '5.0'
        Double.parseDouble(time)
    }

}
