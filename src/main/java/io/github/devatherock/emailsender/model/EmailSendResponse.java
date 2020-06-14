package io.github.devatherock.emailsender.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
     * Unique id for the email request
     */
    @Schema(description = "Unique id for the email request", example = "<267555284.3.1592156660480.JavaMail.abc@localhost>")
    private String id;
}
