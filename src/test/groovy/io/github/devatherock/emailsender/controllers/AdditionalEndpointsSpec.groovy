package io.github.devatherock.emailsender.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test that tests additional endpoints like {@code /actuator/health}
 */
abstract class AdditionalEndpointsSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate
    
    @Value('${test.server.url}')
    String serverUrl    

    @Unroll
    void 'test endpoint - #endpoint'() {
        when:
        ResponseEntity response = restTemplate.getForEntity("${serverUrl}${endpoint}", String)

        then:
        response.status.value() == 200

        where:
        endpoint << [
                '/actuator/health',
                '/actuator/metrics',
                '/actuator/info',
                '/swagger-ui.html',
                '/v3/api-docs'
        ]
    }
}
