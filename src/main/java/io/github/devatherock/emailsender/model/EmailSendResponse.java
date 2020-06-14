package io.github.devatherock.emailsender.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the response of an email send request
 */
@Getter
@Setter
@Builder
public class EmailSendResponse {
    /**
     * The id of the email
     */
    private String id;
}
