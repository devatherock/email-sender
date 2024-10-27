package io.github.devatherock.emailsender.config

import org.springframework.aot.hint.RuntimeHints

import spock.lang.Specification
import spock.lang.Subject

/**
 * Test class for {@link GraalConfig}
 */
class GraalConfigSpec extends Specification {
    @Subject
    GraalConfig config = new GraalConfig()

    void 'test register hints'() {
        given:
        RuntimeHints hints = new RuntimeHints()

        when:
        config.registerHints(hints, this.class.classLoader)

        then:
        hints.resources.resourcePatternHints.size() == 2
    }
}
