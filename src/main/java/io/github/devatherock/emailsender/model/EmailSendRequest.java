package io.github.devatherock.emailsender.model;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

import org.springframework.util.CollectionUtils;

import io.github.devatherock.emailsender.config.EmailSenderProperties.Contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a request to send an email
 */
@Getter
@Setter
public class EmailSendRequest {
    /**
     * The from address to use, in case it is not specified at the application level
     */
    @Valid
    @Schema(description = "The from address to use, in case it is not specified at the application level")
    private Contact from;

    /**
     * List of {@code to} addresses
     */
    @Valid
    @Schema(description = "List of to addresses")
    private List<Contact> to;

    /**
     * List of {@code cc} addresses
     */
    @Valid
    @Schema(description = "List of cc addresses")
    private List<Contact> cc;

    /**
     * List of {@code bcc} addresses
     */
    @Valid
    @Schema(description = "List of bcc addresses")
    private List<Contact> bcc;

    /**
     * Subject of the email
     */
    @Schema(description = "Subject of the email", example = "Test email subject")
    private String subject;

    /**
     * Plain text content of the email
     */
    @Schema(description = "Plain text content of the email", example = "Test email content")
    private String text;

    /**
     * Html content of the email
     */
    @Schema(description = "Html content of the email", example = "<html><body>Test email content</body></html>")
    private String html;

    /**
     * Validates if at least one recipient is specified
     *
     * @return
     */
    @AssertTrue(message = "One of to, cc or bcc must be specified")
    @Schema(accessMode = READ_ONLY)
    public boolean isRecipientSpecified() {
        return !CollectionUtils.isEmpty(to) || !CollectionUtils.isEmpty(cc) || !CollectionUtils.isEmpty(bcc);
    }
}
