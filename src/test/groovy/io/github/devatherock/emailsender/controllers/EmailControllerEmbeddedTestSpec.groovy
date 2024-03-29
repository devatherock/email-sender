package io.github.devatherock.emailsender.controllers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.EmailSenderApplication

/**
 * Unit test for {@link EmailController} class, that uses embedded SMTP server
 */
@ActiveProfiles('embedded')
@SpringBootTest(classes = [EmailSenderApplication],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
class EmailControllerEmbeddedTestSpec extends EmailControllerEmbeddedSpec {

}
