package io.github.devatherock.emailsender;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import io.github.devatherock.emailsender.util.MailUtil;

/***
 * Main class for the application
 *
 * @author devatherock
 **/
@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        MailUtil.init();
        new SpringApplicationBuilder(EmailSenderApplication.class).bannerMode(Mode.LOG).run(args);
    }
}
