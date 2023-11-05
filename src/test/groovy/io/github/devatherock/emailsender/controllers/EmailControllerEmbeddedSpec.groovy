package io.github.devatherock.emailsender.controllers

import groovy.json.JsonOutput

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import spock.lang.Specification

/**
 * Test class for {@link EmailController} class that uses embedded SMTP server
 */
abstract class EmailControllerEmbeddedSpec extends Specification {
    @Autowired
    TestRestTemplate restTemplate

    @Value('${test.server.url}')
    String serverUrl

    void 'test send email'() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity(
                "${serverUrl}/email/v1", new HttpEntity(JsonOutput.toJson(buildRequest()), headers), String
        )

        then:
        response.statusCode.value() == 201
    }

    def buildRequest() {
        return [
                'from'   : [
                        'email': 'devatherock@github.io'
                ],
                'to'     : [
                        [
                                'email': 'w7162k2y@devatherock.anonaddy.com'
                        ]
                ],
                'subject': 'Test email subject',
                'text'   : 'Test plain email content',
                'html'   : '<html><body>Test html email content</body></html>'
        ]
    }
}
