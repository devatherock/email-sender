package io.github.devatherock.emailsender.model;

import io.github.devatherock.emailsender.EmailSenderProperties.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.util.List;

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
    private Contact from;

    /**
     * List of {@code to} addresses
     */
    @Valid
    private List<Contact> to;

    /**
     * List of {@code cc} addresses
     */
    @Valid
    private List<Contact> cc;

    /**
     * List of {@code bcc} addresses
     */
    @Valid
    private List<Contact> bcc;

    /**
     * Subject of the email
     */
    private String subject;

    /**
     * Plain text content of the email
     */
    private String text;

    /**
     * Html content of the email
     */
    private String html;

    /**
     * Validates if at least one recipient is specified
     *
     * @return
     */
    @AssertTrue(message = "One of to, cc or bcc must be specified")
    public boolean isRecipientSpecified() {
        return !CollectionUtils.isEmpty(to) || !CollectionUtils.isEmpty(cc) || !CollectionUtils.isEmpty(bcc);
    }
}
