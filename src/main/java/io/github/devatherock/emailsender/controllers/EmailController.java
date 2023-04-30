package io.github.devatherock.emailsender.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.devatherock.emailsender.model.EmailSendRequest;
import io.github.devatherock.emailsender.model.EmailSendResponse;
import io.github.devatherock.emailsender.service.EmailClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Controller to handle sending emails
 *
 * @author devatherock
 **/
@RestController
@RequiredArgsConstructor
@Tag(name = "email")
public class EmailController {
    private final EmailClient emailClient;

    /**
     * Sends an email accepting a JSON or YAML request
     *
     * @param emailRequest
     * @return {@link EmailSendResponse}
     */
    @PostMapping(path = "/email/v1", consumes = { MediaType.APPLICATION_JSON_VALUE,
            "application/x-yaml" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Sends an email accepting a JSON or YAML request")
    public EmailSendResponse sendEmail(@Valid @RequestBody EmailSendRequest emailRequest) {
        return EmailSendResponse.builder().id(emailClient.sendEmail(emailRequest)).build();
    }
}