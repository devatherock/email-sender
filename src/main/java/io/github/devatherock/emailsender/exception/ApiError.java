package io.github.devatherock.emailsender.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing an error
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private String field;
    private String message;
}
