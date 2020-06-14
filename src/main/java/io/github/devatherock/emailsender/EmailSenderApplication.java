package io.github.devatherock.emailsender;

import io.micronaut.runtime.Micronaut;
import io.micronaut.spring.context.MicronautApplicationContext;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.filter.CommonsRequestLoggingFilter;

/***
 * Main class for the application
 *
 * @author devatherock
 **/
@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        Micronaut.run(EmailSenderApplication.class);
    }

    /**
     * Initializes a filter to log requests
     *
     * @return a {@link CommonsRequestLoggingFilter}
     */
    /*@Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        return new CommonsRequestLoggingFilter();
    }*/
}
