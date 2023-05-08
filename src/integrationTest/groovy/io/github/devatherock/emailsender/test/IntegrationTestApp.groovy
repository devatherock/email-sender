package io.github.devatherock.emailsender.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class IntegrationTestApp {

    static void main(String[] args) {
       SpringApplication.run(IntegrationTestApp, args)
    }
}
