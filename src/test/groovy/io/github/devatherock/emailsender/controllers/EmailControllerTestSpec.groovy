package io.github.devatherock.emailsender.controllers

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.EmailSenderApplication

/**
 * Unit test for {@link EmailController} class
 */
@ActiveProfiles('test')
@SpringBootTest(classes = [EmailSenderApplication],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration // https://github.com/spockframework/spock/issues/1539
class EmailControllerTestSpec extends EmailControllerSpec {

}
