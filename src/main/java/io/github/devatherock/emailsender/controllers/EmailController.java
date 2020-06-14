package io.github.devatherock.emailsender.controllers;

import io.github.devatherock.emailsender.model.EmailSendRequest;
import io.github.devatherock.emailsender.model.EmailSendResponse;
import io.github.devatherock.emailsender.service.EmailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

/**
 * Controller to handle sending emails
 *
 * @author devatherock
 **/
@RestController
public class EmailController {
    @Autowired
    private EmailClient emailClient;

    @Autowired
    private Validator validator;

    /**
     * Handles an email send request with a JSON payload
     *
     * @param emailRequest
     * @return {@link EmailSendResponse}
     */
    @PostMapping(path = "/email/v1", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailSendResponse sendEmail(@Valid @RequestBody EmailSendRequest emailRequest) {
        return EmailSendResponse.builder().id(emailClient.sendEmail(emailRequest)).build();
    }

    /**
     * Handles an email send request with a YAML payload
     *
     * @param request
     * @return {@link EmailSendResponse}
     */
    @PostMapping(path = "/email/v1", consumes = "application/x-yaml")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailSendResponse sendEmail(@RequestBody String request) {
        Yaml yaml = new Yaml();
        EmailSendRequest emailRequest = yaml.loadAs(request, EmailSendRequest.class);
        Set<ConstraintViolation<EmailSendRequest>> violations = validator.validate(emailRequest);

        if (CollectionUtils.isEmpty(violations)) {
            return EmailSendResponse.builder().id(emailClient.sendEmail(emailRequest)).build();
        } else {
            throw new ConstraintViolationException(violations);
        }
    }
}