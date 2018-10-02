package io.github.devaprasadh.emailsenderapi.controllers;

import javax.validation.Valid;

import io.github.devaprasadh.emailsenderapi.model.EmailSendResponse;
import io.github.devaprasadh.emailsenderapi.service.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.devaprasadh.emailsenderapi.model.EmailSendRequest;

/**
 * Controller to handle sending emails
 *
 * @author devatherock
 **/
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailClient emailClient;


    @PostMapping("/email/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailSendResponse sendEmail(@Valid @RequestBody EmailSendRequest emailRequest) {
        return EmailSendResponse.builder().id(emailClient.sendEmail(emailRequest)).build();
    }
}