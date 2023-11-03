package io.github.devatherock.emailsender.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing an error response
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {
    private List<ApiError> errors;
}
