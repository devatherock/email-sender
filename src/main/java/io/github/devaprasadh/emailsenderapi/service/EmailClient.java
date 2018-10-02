package io.github.devaprasadh.emailsenderapi.service;

import io.github.devaprasadh.emailsenderapi.EmailSenderProperties;
import io.github.devaprasadh.emailsenderapi.model.EmailSendRequest;
import lombok.RequiredArgsConstructor;
import org.masukomi.aspirin.Aspirin;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailClient {
    private final EmailSenderProperties config;
    private Mailer mailer;

    @PostConstruct
    public void init() {
        if (!config.isEmbeddedSmtp()) {
            mailer = MailerBuilder
                    .withSMTPServer(config.getSmtp().getHost(), config.getSmtp().getPort(), config.getSmtp().getUsername(),
                            config.getSmtp().getPassword()).buildMailer();
        }
    }

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
        contentId = UUID.randomUUID().toString();

        try {
            if (config.isEmbeddedSmtp()) {
                MimeMessage message = EmailConverter.emailToMimeMessage(email);
                message.setContentID(contentId);
                id = message.getMessageID();
                Aspirin.add(message);
            } else {
                mailer.sendMail(email);
                id = email.getId();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return null != id ? id : contentId;
    }
}
