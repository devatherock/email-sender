package io.github.devatherock.emailsender.controllers

import javax.mail.Message

import groovy.json.JsonOutput

import org.simplejavamail.email.Email
import org.simplejavamail.mailer.Mailer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import io.github.devatherock.emailsender.EmailSenderApplication

import spock.lang.Specification
import spock.mock.DetachedMockFactory

/**
 * Test class for {@link EmailController} class
 */
@ActiveProfiles('test')
@SpringBootTest(classes = [EmailSenderApplication, TestConfig],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration // https://github.com/spockframework/spock/issues/1539
class EmailControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    Mailer mockMailer

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
                'text'   : 'Test email content',
                'html'   : '<html><body>Test email content</body></html>'
        ]

        and:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity('/email/v1',
                new HttpEntity(JsonOutput.toJson(request), headers), String)

        then:
        response.statusCodeValue == 201

        and:
        1 * mockMailer.validate({ Email email ->
            email.subject == 'Test email subject'
            email.getHTMLText() == '<html><body>Test email content</body></html>'
            email.plainText == 'Test email content'
            email.fromRecipient.name == 'Test.From'
            email.fromRecipient.address == 'from@test.com'
        }) >> { args ->
            Email capturedEmail = args[0]
            
            verifyRecipient(capturedEmail.recipients, Message.RecipientType.TO, 'Test.To', 'to@test.com')
            verifyRecipient(capturedEmail.recipients, Message.RecipientType.BCC, 'Test.Bcc', 'bcc@test.com')
            verifyRecipient(capturedEmail.recipients, Message.RecipientType.CC, 'Test.Cc', 'cc@test.com')
        
        	return false
        }
    }
    
    private void verifyRecipient(List recipients, Message.RecipientType type, String name, String email) {
    	def recipient = recipients.find { it.type == type }
    	
    	assert recipient.name == name
    	assert recipient.address == email
    }

    @TestConfiguration
    static class TestConfig {
        def mockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        Mailer mockMailer() {
            return mockFactory.Mock(Mailer)
        }
    }
}
