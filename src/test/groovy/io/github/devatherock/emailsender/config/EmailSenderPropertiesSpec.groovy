package io.github.devatherock.emailsender.config

import spock.lang.Specification
import spock.lang.Subject

/**
 * Test class for {@link EmailSenderProperties}
 */
class EmailSenderPropertiesSpec extends Specification {
    @Subject
    EmailSenderProperties config = new EmailSenderProperties()

    void 'test is valid smtp config'() {
        given:
        config.smtp.embedded.enabled = isEmbedded
        config.smtp.embedded.host = embeddedHost
        config.smtp.username = username
        config.smtp.password = password
        config.smtp.host = host

        expect:
        config.isValidSmtpConfig() == expected

        where:
        isEmbedded | username   | password   | host         | expected | embeddedHost
        true       | null       | null       | null         | true     | 'embedded.host'
        true       | ''         | ''         | ''           | true     | 'embedded.host'
        true       | ' '        | ' '        | ' '          | true     | 'embedded.host'
        true       | null       | null       | null         | false    | null
        true       | ''         | ''         | ''           | false    | ''
        true       | ' '        | ' '        | ' '          | false    | ' '
        false      | 'testuser' | 'password' | null         | false    | null
        false      | 'testuser' | 'password' | ''           | false    | null
        false      | 'testuser' | 'password' | ' '          | false    | null
        false      | null       | 'password' | 'dummy.host' | false    | null
        false      | ''         | 'password' | 'dummy.host' | false    | null
        false      | ' '        | 'password' | 'dummy.host' | false    | null
        false      | 'testuser' | null       | null         | false    | null
        false      | 'testuser' | ''         | 'dummy.host' | false    | null
        false      | 'testuser' | ' '        | 'dummy.host' | false    | null
        false      | null       | null       | null         | false    | null
        false      | 'testuser' | 'password' | 'dummy.host' | true     | null
    }
}
