package io.github.augustoerico.config

class Env {

    static port() {
        def port = System.getenv().PORT ?: '3000'
        Integer.parseInt(port)
    }

    static address() {
        System.getenv().ADDRESS ?: '0.0.0.0'
    }

    static mongoDbUri() {
        System.getenv().MONGO_DB_URI ?:
                'mongodb://zombie-market-user:zombie-market-password@ds023480.mlab.com:23480/zombie-market-db'
    }

    static mongoDbName() {
        System.getenv().MONGO_DB_NAME ?: 'zombie-market-db'
    }

    /**
     * Tests
     */
    static Double testWaitTime() {
        def time = System.getenv().TEST_WAIT_TIME ?: '5.0'
        Double.parseDouble(time)
    }

}
