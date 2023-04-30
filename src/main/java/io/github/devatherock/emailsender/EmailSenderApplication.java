package io.github.devatherock.emailsender;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/***
 * Main class for the application
 *
 * @author devatherock
 **/
@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EmailSenderApplication.class).bannerMode(Mode.LOG).run(args);
    }
}
