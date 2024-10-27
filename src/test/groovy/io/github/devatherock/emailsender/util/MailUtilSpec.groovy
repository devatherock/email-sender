package io.github.devatherock.emailsender.util

import javax.activation.CommandMap

import spock.lang.Specification

/**
 * Test class for {@link MailUtil}
 */
class MailUtilSpec extends Specification {

    void 'test init'() {
        when:
        MailUtil.init()

        then:
        CommandMap.defaultCommandMap.mimeTypes.length >= 5
    }
}
