package io.github.devatherock.emailsender.service;

import io.github.devatherock.emailsender.EmailSenderProperties;
import io.github.devatherock.emailsender.model.EmailSendRequest;
import lombok.RequiredArgsConstructor;
import org.masukomi.aspirin.Aspirin;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * Service to send emails
 */
@Component
@RequiredArgsConstructor
public class EmailClient {
    private final EmailSenderProperties config;
    private Mailer mailer;

    /**
     * Initializes connection to an SMTP server when embedded SMTP isn't enabled
     */
    @PostConstruct
    public void init() {
        if (!config.getSmtp().isEmbedded()) {
            mailer = MailerBuilder
                    .withSMTPServer(config.getSmtp().getHost(), config.getSmtp().getPort(),
                            config.getSmtp().getUsername(), config.getSmtp().getPassword()).buildMailer();
        }
    }

    /**
     * Sends an email out
     *
     * @param emailSendRequest
     * @return the id of the email
     */
    public String sendEmail(EmailSendRequest emailSendRequest) {
        String id = null;
        String contentId = null;
        Email email = transformRequest(emailSendRequest);

        try {
            if (config.getSmtp().isEmbedded()) {
                MimeMessage message = EmailConverter.emailToMimeMessage(email);
                contentId = UUID.randomUUID().toString();
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

        if (id == null) {
            id = contentId != null ? contentId : UUID.randomUUID().toString();
        }
        MDC.put("id", id);

        return id;
    }

    /**
     * Transforms an {@link EmailSendRequest} into an {@link Email}
     *
     * @param emailSendRequest
     * @return an email
     */
    private Email transformRequest(EmailSendRequest emailSendRequest) {
        EmailPopulatingBuilder emailBuilder = EmailBuilder.startingBlank()
                .withSubject(emailSendRequest.getSubject())
                .withHTMLText(emailSendRequest.getHtml())
                .withPlainText(emailSendRequest.getText());

        // From address
        if (null != config.getSmtp().getFromAddress()) {
            emailBuilder.from(config.getSmtp().getFromAddress().getName(),
                    config.getSmtp().getFromAddress().getEmail());
        }

        // If using the embedded SMTP server, let emails be sent with from address in request
        if (null != emailSendRequest.getFrom() && (null == config.getSmtp().getFromAddress() ||
                config.getSmtp().isEmbedded())) {
            emailBuilder.from(emailSendRequest.getFrom().getName(), emailSendRequest.getFrom().getEmail());
        }

        // To addresses
        if (!CollectionUtils.isEmpty(emailSendRequest.getTo())) {
            emailSendRequest.getTo().forEach(to -> {
                emailBuilder.to(to.getName(), to.getEmail());
            });
        }

        // Bcc addresses
        if (!CollectionUtils.isEmpty(emailSendRequest.getBcc())) {
            emailSendRequest.getBcc().forEach(bcc -> {
                emailBuilder.bcc(bcc.getName(), bcc.getEmail());
            });
        }

        // cc addresses
        if (!CollectionUtils.isEmpty(emailSendRequest.getCc())) {
            emailSendRequest.getCc().forEach(cc -> {
                emailBuilder.cc(cc.getName(), cc.getEmail());
            });
        }

        return emailBuilder.buildEmail();
    }
}
