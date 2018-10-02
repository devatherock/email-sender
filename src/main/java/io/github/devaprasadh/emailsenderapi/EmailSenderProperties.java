package io.github.devaprasadh.emailsenderapi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;

@ConfigurationProperties("emailsender")
@Getter
@Setter
@Component
@Validated
public class EmailSenderProperties {
    private boolean embeddedSmtp = false;

    private SmtpProperties smtp = new SmtpProperties();

    @Getter
    @Setter
    public static class SmtpProperties {
        private String username;
        private String password;
        private String host;
        private int port = 587;
    }

    @AssertTrue
    public boolean isValidSmtpConfig() {
        return isEmbeddedSmtp() || !(StringUtils.isEmpty(smtp.getUsername()) || StringUtils.isEmpty(smtp.getPassword())
                || StringUtils.isEmpty(smtp.getHost()));
    }
}
