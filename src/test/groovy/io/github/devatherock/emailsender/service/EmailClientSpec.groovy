package io.github.devatherock.emailsender.service

import javax.mail.Message.RecipientType

import org.simplejavamail.email.Email
import org.simplejavamail.mailer.Mailer

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

    void setup() {
        emailClient = new EmailClient(Optional.of(mailer), new EmailSenderProperties())
    }

    void 'test send email'() {
        given:
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
                'cc': [
                        new Contact(
                                name: 'Test.Cc',
                                email: 'cc@test.com'
                        )
                ],
                bcc: [
                        new Contact(
                                name: 'Test.Bcc',
                                email: 'bcc@test.com'
                        )
                ],
                subject: 'Test email subject',
                text: 'Test email content',
                html: '<html><body>Test email content</body></html>'
        )

        when:
        String id = emailClient.sendEmail(request)

        then:
        1 * mailer.validate({ Email email ->
            email.subject == 'Test email subject'
            email.getHTMLText() == '<html><body>Test email content</body></html>'
            email.plainText == 'Test email content'
            email.fromRecipient.name == 'Test.From'
            email.fromRecipient.address == 'from@test.com'
        }) >> { args ->
            Email capturedEmail = args[0]

            verifyRecipient(capturedEmail.recipients, RecipientType.TO, 'Test.To', 'to@test.com')
            verifyRecipient(capturedEmail.recipients, RecipientType.BCC, 'Test.Bcc', 'bcc@test.com')
            verifyRecipient(capturedEmail.recipients, RecipientType.CC, 'Test.Cc', 'cc@test.com')

            capturedEmail.id = 'dummy-id'

            return false
        }

        and:
        id == 'dummy-id'
    }

    private void verifyRecipient(List recipients, RecipientType type, String name, String email) {
        def recipient = recipients.find { it.type == type }

        assert recipient.name == name
        assert recipient.address == email
    }
}
