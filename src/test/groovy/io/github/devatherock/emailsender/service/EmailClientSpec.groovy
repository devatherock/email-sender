package io.github.devatherock.emailsender.service

import javax.mail.Message.RecipientType

import org.simplejavamail.api.email.Email
import org.simplejavamail.api.mailer.Mailer

import io.github.devatherock.emailsender.config.EmailSenderProperties
import io.github.devatherock.emailsender.config.EmailSenderProperties.Contact
import io.github.devatherock.emailsender.model.EmailSendRequest

import spock.lang.Specification
import spock.lang.Subject

/**
 * Test class for {@link EmailClient}
 */
class EmailClientSpec extends Specification {

    @Subject
    EmailClient emailClient

    Mailer mailer = Mock()
    EmailSenderProperties config = new EmailSenderProperties()

    void setup() {
        emailClient = new EmailClient(Optional.of(mailer), config)
    }

    void 'test transform request - no from email in request'() {
        given:
        config.smtp.fromAddress = new Contact(name: 'Global.From', email: 'from.global@test.com')

        and:
        EmailSendRequest request = new EmailSendRequest(
                'to': [
                        new Contact(
                                name: 'Test.To',
                                email: 'to@test.com'
                        )
                ],
                subject: 'Test email subject',
                text: 'Test email content',
                html: '<html><body>Test email content</body></html>'
        )

        when:
        Email email = emailClient.transformRequest(request)

        then:
        email.subject == 'Test email subject'
        email.getHTMLText() == '<html><body>Test email content</body></html>'
        email.plainText == 'Test email content'
        email.fromRecipient.name == 'Global.From'
        email.fromRecipient.address == 'from.global@test.com'

        and:
        verifyRecipient(email.recipients, RecipientType.TO, 'Test.To', 'to@test.com')
    }

    void 'test transform request - from email present in request and config when not using embedded server'() {
        given:
        config.smtp.fromAddress = new Contact(name: 'Global.From', email: 'from.global@test.com')

        and:
        EmailSendRequest request = new EmailSendRequest(
                from: new Contact(
                        name: 'Test.From',
                        email: 'from@test.com'
                ),
                'to': [
                        new Contact(
                                name: 'Test.To',
                                email: 'to@test.com'
                        )
                ],
                subject: 'Test email subject',
                text: 'Test email content',
                html: '<html><body>Test email content</body></html>'
        )

        when:
        Email email = emailClient.transformRequest(request)

        then:
        email.subject == 'Test email subject'
        email.getHTMLText() == '<html><body>Test email content</body></html>'
        email.plainText == 'Test email content'
        email.fromRecipient.name == 'Global.From'
        email.fromRecipient.address == 'from.global@test.com'

        and:
        verifyRecipient(email.recipients, RecipientType.TO, 'Test.To', 'to@test.com')
    }

    private void verifyRecipient(List recipients, RecipientType type, String name, String email) {
        def recipient = recipients.find { it.type == type }

        assert recipient.name == name
        assert recipient.address == email
    }
}
