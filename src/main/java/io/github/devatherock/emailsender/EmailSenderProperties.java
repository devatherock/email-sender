package io.github.devatherock.emailsender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Configurable properties for the application
 */
@ConfigurationProperties("emailsender")
@Getter
@Setter
@Component
@Validated
public class EmailSenderProperties {
    /**
     * Indicates if an embedded SMTP server should be used. Turned off by default
     */
    private boolean embeddedSmtp = false;

    @Valid
    private SmtpProperties smtp = new SmtpProperties();

    /**
     * SMTP configuration
     */
    @Getter
    @Setter
    public static class SmtpProperties {
        /**
         * The SMTP username
         */
        private String username;
        /**
         * The SMTP username
         */
        private String password;
        /**
         * The SMTP server name
         */
        private String host;
        /**
         * The SMTP port. Defaults to 587
         */
        private int port = 587;

        /**
         * The from address to use in outbound emails
         */
        @Valid
        private Contact fromAddress;
    }

    /**
     * Validates if all required attributes are specified
     *
     * @return
     */
    @AssertTrue(message = "SMTP server details missing")
    public boolean isValidSmtpConfig() {
        return isEmbeddedSmtp() || !(StringUtils.isEmpty(smtp.getUsername()) || StringUtils.isEmpty(smtp.getPassword())
                || StringUtils.isEmpty(smtp.getHost()));
    }

    /**
     * Class representing a contact
     */
    @Getter
    @Setter
    public static class Contact {
        /**
         * The name of the contact. Optional
         */
        private String name;

        /**
         * The email address of the contact. Required
         */
        @Email
        @NotBlank
        private String email;
    }
}
