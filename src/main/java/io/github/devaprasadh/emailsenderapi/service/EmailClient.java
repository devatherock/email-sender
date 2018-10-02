package io.github.devaprasadh.emailsenderapi.service;

import io.github.devaprasadh.emailsenderapi.model.EmailSendRequest;
import org.masukomi.aspirin.Aspirin;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailClient {

    public String sendEmail(EmailSendRequest emailSendRequest) {
        String id = null;
        String contentId = null;

        Email email = EmailBuilder.startingBlank()
                .from(null, emailSendRequest.getFrom())
                .to(null, emailSendRequest.getTo())
                .withSubject(emailSendRequest.getSubject())
                .withHTMLText(emailSendRequest.getHtml())
                .withPlainText(emailSendRequest.getText())
                .buildEmail();
        MimeMessage message = EmailConverter.emailToMimeMessage(email);
        contentId = UUID.randomUUID().toString();

        try {
            message.setContentID(contentId);
            id = message.getMessageID();
            Aspirin.add(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return null != id ? id : contentId;
    }
}
