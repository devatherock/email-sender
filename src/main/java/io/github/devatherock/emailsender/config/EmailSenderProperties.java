package io.github.devatherock.emailsender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
     * @return a flag
     */
    @AssertTrue(message = "SMTP server details missing")
    public boolean isValidSmtpConfig() {
        return smtp.embedded || (StringUtils.hasText(smtp.username) && StringUtils.hasText(smtp.password)
                && StringUtils.hasText(smtp.host));
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
