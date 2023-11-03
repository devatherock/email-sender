package io.github.devatherock.emailsender.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller advice to handle common exceptions
 */
@RestControllerAdvice
public class GenericApiExceptionHandler {

    /**
     * Handles a {@link MethodArgumentNotValidException}
     * 
     * @param exception
     * @return an {@link ApiErrorResponse}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ApiError> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ApiErrorResponse(errors);
    }
}
