package io.github.devatherock.emailsender.config;

import org.masukomi.aspirin.store.mail.SimpleMailStore;
import org.masukomi.aspirin.store.queue.SimpleQueueStore;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sun.mail.handlers.handler_base;
import com.sun.mail.handlers.image_gif;
import com.sun.mail.handlers.image_jpeg;
import com.sun.mail.handlers.message_rfc822;
import com.sun.mail.handlers.multipart_mixed;
import com.sun.mail.handlers.text_html;
import com.sun.mail.handlers.text_plain;
import com.sun.mail.handlers.text_xml;
import com.sun.mail.smtp.SMTPTransport;

@Configuration
@RegisterReflectionForBinding({
        SimpleQueueStore.class,
        SimpleMailStore.class,
        SMTPTransport.class,
        multipart_mixed.class,
        text_html.class,
        handler_base.class,
        image_gif.class,
        image_jpeg.class,
        message_rfc822.class,
        text_plain.class,
        text_xml.class,
})
public class GraalConfig implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerResource(new ClassPathResource("META-INF/mailcap"));
        hints.resources().registerResource(new ClassPathResource("META-INF/mailcap.default"));
    }
}
