package io.github.devatherock.emailsender.controllers

import javax.mail.Address
import javax.mail.Message.RecipientType
import javax.mail.internet.MimeMessage

import groovy.json.JsonOutput

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.subethamail.wiser.Wiser
import org.subethamail.wiser.WiserMessage
import org.yaml.snakeyaml.Yaml

import com.jayway.jsonpath.JsonPath
import spock.lang.Shared
import spock.lang.Specification

/**
 * Test class for {@link EmailController} class
 */
abstract class EmailControllerSpec extends Specification {

    @Shared
    Wiser wiser = new Wiser()

    @Autowired
    TestRestTemplate restTemplate

    @Value('${test.server.url}')
    String serverUrl

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

    void 'test send email - #contentType'() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(contentType)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity(
                "${serverUrl}/email/v1", new HttpEntity(requestBody, headers), String
        )

        then:
        response.statusCode.value() == 201

        and:
        wiser.messages.size() == 3
        verifyMessage(wiser.messages[0], 'to@test.com')
        verifyMessage(wiser.messages[1], 'cc@test.com')
        verifyMessage(wiser.messages[2], 'bcc@test.com')

        where:
        contentType                            | requestBody
        MediaType.APPLICATION_JSON             | JsonOutput.toJson(buildRequest())
        new MediaType("application", "x-yaml") | new Yaml().dump(buildRequest())
    }

    void 'test send email with only #recipientType'() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity(
                "${serverUrl}/email/v1",
                new HttpEntity(JsonOutput.toJson(buildRequest(fieldsToRemove)), headers),
                String
        )

        then:
        response.statusCode.value() == 201

        and:
        wiser.messages.size() == 1
        WiserMessage message = wiser.messages[0]

        and:
        verifyContent(message.mimeMessage)

        and:
        verifyAddress(message.mimeMessage.from[0], 'Test.From', 'from@test.com')
        verifyAddress(message.mimeMessage.getRecipients(recipientType)[0], recipientName, recipientEmail)
        !message.mimeMessage.getRecipients(skippedRecipientType)
        message.envelopeReceiver == recipientEmail

        where:
        recipientType    | fieldsToRemove | recipientName | recipientEmail | skippedRecipientType
        RecipientType.TO | ['cc', 'bcc']  | 'Test.To'     | 'to@test.com'  | RecipientType.CC
        RecipientType.CC | ['to', 'bcc']  | 'Test.Cc'     | 'cc@test.com'  | RecipientType.TO
    }

    void 'test send email with only bcc'() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity(
                "${serverUrl}/email/v1",
                new HttpEntity(JsonOutput.toJson(buildRequest(['to', 'cc'])), headers),
                String
        )

        then:
        response.statusCode.value() == 201

        and:
        wiser.messages.size() == 1
        WiserMessage message = wiser.messages[0]

        and:
        verifyContent(message.mimeMessage)

        and:
        verifyAddress(message.mimeMessage.from[0], 'Test.From', 'from@test.com')
        !message.mimeMessage.getRecipients(RecipientType.TO)
        !message.mimeMessage.getRecipients(RecipientType.CC)
        message.envelopeReceiver == 'bcc@test.com'
    }

    void 'test send email - no recipients specified'() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept([MediaType.APPLICATION_JSON])

        when:
        ResponseEntity response = restTemplate.postForEntity(
                "${serverUrl}/email/v1", new HttpEntity('{}', headers), String
        )

        then:
        response.statusCode.value() == 400
        JsonPath.read(response.body, '$.errors')
        JsonPath.read(response.body, '$.errors.length()') == 1
        JsonPath.read(response.body, '$.errors[0].field') == 'recipientSpecified'
        JsonPath.read(response.body, '$.errors[0].message') == 'One of to, cc or bcc must be specified'

        and:
        !wiser.messages
    }

    protected void verifyAddress(Address address, String name, String email) {
        assert address.personal == name
        assert address.address == email
    }

    void verifyContent(MimeMessage message) {
        String emailContent = message.content.ds.inputStream.text

        assert message.subject == 'Test email subject'
        assert emailContent.contains('Test plain email content')
        assert emailContent.contains('<html><body>Test html email content</body></html>')
    }

    void verifyMessage(WiserMessage wiserMessage, String recipient) {
        MimeMessage message = wiserMessage.mimeMessage

        verifyContent(message)
        verifyAddress(message.from[0], 'Test.From', 'from@test.com')
        verifyAddress(message.getRecipients(RecipientType.TO)[0], 'Test.To', 'to@test.com')
        verifyAddress(message.getRecipients(RecipientType.CC)[0], 'Test.Cc', 'cc@test.com')
        assert wiserMessage.envelopeReceiver == recipient
    }

    def buildRequest(def fieldsToRemove = []) {
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

        fieldsToRemove.each { request.remove(it) }

        return request
    }
}
