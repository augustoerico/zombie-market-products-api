package io.github.augustoerico.config

class Env {

    static port() {
        def port = System.getenv().PORT ?: '3000'
        Integer.parseInt(port)
    }

    static address() {
        System.getenv().ADDRESS ?: '127.0.0.1'
    }

}
