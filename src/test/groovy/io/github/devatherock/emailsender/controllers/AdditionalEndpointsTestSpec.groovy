package io.github.devatherock.emailsender.controllers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.EmailSenderApplication

/**
 * Unit test for additional endpoints
 */
@ActiveProfiles('test')
@SpringBootTest(classes = [EmailSenderApplication],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class AdditionalEndpointsTestSpec extends AdditionalEndpointsSpec {
}
