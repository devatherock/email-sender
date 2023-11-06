package io.github.devatherock.emailsender.controllers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.test.IntegrationTestApp

/**
 * Integration test for {@link EmailController} class, that uses embedded SMTP server
 */
@ActiveProfiles('integration')
@SpringBootTest(classes = [IntegrationTestApp],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class EmailControllerEmbeddedIntegrationSpec extends EmailControllerEmbeddedSpec {

}
