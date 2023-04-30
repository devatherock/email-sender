package io.github.devatherock.emailsender.config;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Configurable properties for the application
 */
@ConfigurationProperties("emailsender")
@Getter
@Setter
@Component
@Validated
public class EmailSenderProperties {
    @Valid
    private SmtpProperties smtp = new SmtpProperties();

    /**
     * SMTP configuration
     */
    @Getter
    @Setter
    public static class SmtpProperties {
        /**
         * Indicates if an embedded SMTP server should be used. Turned off by default
         */
        private boolean embedded = false;
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
        return smtp.embedded || !(StringUtils.isEmpty(smtp.username) || StringUtils.isEmpty(smtp.password)
                || StringUtils.isEmpty(smtp.host));
    }

    /**
     * Class representing a contact
     */
    @Getter
    @Setter
    public static class Contact {
        /**
         * The name of the recipient. Optional
         */
        @Schema(description = "The name of the recipient. Optional", example = "Test.User")
        private String name;

        /**
         * The email address of the recipient. Required
         */
        @Email
        @NotBlank
        @Schema(description = "The email address of the recipient. Required", example = "test@test.com")
        private String email;
    }
}
