package io.github.devatherock.emailsender.controllers

import javax.mail.Address
import javax.mail.Message.RecipientType

import groovy.json.JsonOutput

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.subethamail.wiser.Wiser
import org.subethamail.wiser.WiserMessage

import io.github.devatherock.emailsender.EmailSenderApplication

import spock.lang.Shared
import spock.lang.Specification

/**
 * Test class for {@link EmailController} class
 */
@ActiveProfiles('test')
@SpringBootTest(classes = [EmailSenderApplication],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration // https://github.com/spockframework/spock/issues/1539
class EmailControllerSpec extends Specification {

    @Shared
    Wiser wiser = new Wiser()

    @Autowired
    TestRestTemplate restTemplate

    void setupSpec() {
        wiser.setPort(2500)
        wiser.start()
    }

    void cleanupSpec() {
        wiser.stop()
    }

    void cleanup() {
        wiser.messages.clear()
    }

    void 'test send email'() {
        given:
        def request = [
                'from'   : [
                        'name' : 'Test.From',
                        'email': 'from@test.com'
                ],
                'to'     : [
                        [
                                'name' : 'Test.To',
                                'email': 'to@test.com'
                        ]
                ],
                'cc'     : [
                        [
                                'name' : 'Test.Cc',
                                'email': 'cc@test.com'
                        ]
                ],
                'bcc'    : [
                        [
                                'name' : 'Test.Bcc',
                                'email': 'bcc@test.com'
                        ]
                ],
                'subject': 'Test email subject',
                'text'   : 'Test plain email content',
                'html'   : '<html><body>Test html email content</body></html>'
        ]

        and:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity('/email/v1',
                new HttpEntity(JsonOutput.toJson(request), headers), String)

        then:
        response.statusCode.value() == 201

        and:
        wiser.messages
        WiserMessage message = wiser.messages[0]
        message.mimeMessage.subject == 'Test email subject'

        and:
        String emailContent = message.mimeMessage.content.ds.inputStream.text
        emailContent.contains('Test plain email content')
        emailContent.contains('<html><body>Test html email content</body></html>')

        and:
        verifyAddress(message.mimeMessage.from[0], 'Test.From', 'from@test.com')
        verifyAddress(message.mimeMessage.getRecipients(RecipientType.TO)[0], 'Test.To', 'to@test.com')
        verifyAddress(message.mimeMessage.getRecipients(RecipientType.CC)[0], 'Test.Cc', 'cc@test.com')
    }

    private void verifyAddress(Address address, String name, String email) {
        assert address.personal == name
        assert address.address == email
    }
}