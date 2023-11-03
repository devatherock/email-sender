package io.github.devatherock.emailsender.model

import io.github.devatherock.emailsender.config.EmailSenderProperties.Contact

import spock.lang.Specification
import spock.lang.Subject

/**
 * Test class for {@link EmailSendRequest}
 */
class EmailSendRequestSpec extends Specification {
    @Subject
    EmailSendRequest request = new EmailSendRequest()

    void 'test is recipient specified'() {
        given:
        request.to = to
        request.cc = cc
        request.bcc = bcc

        expect:
        request.isRecipientSpecified() == expected

        where:
        to                             | cc                             | bcc                            || expected
        null                           | null                           | null                           || false
        []                             | []                             | []                             || false
        [new Contact(email: 'a@b.in')] | []                             | []                             || true
        []                             | [new Contact(email: 'a@b.in')] | []                             || true
        []                             | []                             | [new Contact(email: 'a@b.in')] || true
    }
}
