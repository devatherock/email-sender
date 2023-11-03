package io.github.devatherock.emailsender.util

import org.springframework.http.HttpOutputMessage
import org.springframework.mock.http.MockHttpOutputMessage

import spock.lang.Specification
import spock.lang.Subject

/**
 * Test class for {@link YamlHttpMessageConverter}
 */
class YamlHttpMessageConverterSpec extends Specification {
    @Subject
    YamlHttpMessageConverter converter = new YamlHttpMessageConverter()

	void 'test write internal'() {
	    given:
	    HttpOutputMessage outputMessage = new MockHttpOutputMessage()
	    
	    when:
	    converter.writeInternal(['key': 'value'], outputMessage)
	    
	    then:
	    outputMessage.bodyAsString == 'key: value\n'
	}
}