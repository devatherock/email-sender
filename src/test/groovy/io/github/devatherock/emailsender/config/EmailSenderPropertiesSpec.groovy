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
        config.smtp.embedded = isEmbedded
        config.smtp.username = username
        config.smtp.password = password
        config.smtp.host = host

        expect:
        config.isValidSmtpConfig() == expected

        where:
        isEmbedded | username   | password   | host         | expected
        true       | null       | null       | null         | true
        true       | ''         | ''         | ''           | true
        true       | ' '        | ' '        | ' '          | true
        false      | 'testuser' | 'password' | null         | false
        false      | 'testuser' | 'password' | ''           | false
        false      | 'testuser' | 'password' | ' '          | false
        false      | null       | 'password' | 'dummy.host' | false
        false      | ''         | 'password' | 'dummy.host' | false
        false      | ' '        | 'password' | 'dummy.host' | false
        false      | 'testuser' | null       | null         | false
        false      | 'testuser' | ''         | 'dummy.host' | false
        false      | 'testuser' | ' '        | 'dummy.host' | false
        false      | null       | null       | null         | false
        false      | 'testuser' | 'password' | 'dummy.host' | true
    }
}
