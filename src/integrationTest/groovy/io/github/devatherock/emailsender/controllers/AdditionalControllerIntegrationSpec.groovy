package io.github.devatherock.emailsender.controllers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.test.IntegrationTestApp

/**
 * Integration test for additional endpoints
 */
@ActiveProfiles('integration')
@SpringBootTest(classes = [IntegrationTestApp],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class AdditionalControllerIntegrationSpec extends AdditionalEndpointsSpec {
}
