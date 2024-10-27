package io.github.devatherock.emailsender.config;

import java.util.List;

import org.masukomi.aspirin.Aspirin;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.devatherock.emailsender.util.YamlHttpMessageConverter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Configuration for the application
 */
@Configuration
@RequiredArgsConstructor
public class EmailSenderAppConfig implements BeanFactoryAware {
    private final EmailSenderProperties config;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void init() {
        if (config.getSmtp().getEmbedded().isEnabled()) {
            Aspirin.getConfiguration().setHostname(config.getSmtp().getEmbedded().getHost());
        }
        // Hacky conditional bean registration required for native image
        // @ConditionalOnProperty(name = "emailsender.smtp.embedded.enabled",
        // havingValue = "false") wasn't working
        else {
            // Initializes connection to an SMTP server when embedded SMTP isn't enabled
            Mailer mailer = MailerBuilder
                    .withSMTPServer(config.getSmtp().getHost(), config.getSmtp().getPort(),
                            config.getSmtp().getUsername(), config.getSmtp().getPassword())
                    .buildMailer();
            ((ConfigurableBeanFactory) beanFactory).registerSingleton("mailer", mailer);
        }
    }

    /**
     * {@link WebMvcConfigurer} to accept Yaml input
     *
     * @return {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer webMvcMessageConverterConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new YamlHttpMessageConverter<>());
            }
        };
    }

    /**
     * Initializes a filter to log requests
     *
     * @return a {@link CommonsRequestLoggingFilter}
     */
    @Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        return new CommonsRequestLoggingFilter();
    }

    /**
     * Initializes an {@link OpenAPI} bean for swagger doc generation
     *
     * @param buildProperties
     * @return {@link OpenAPI}
     */
    @Bean
    public OpenAPI openAPI(BuildProperties buildProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title("Email Sender")
                        .version(buildProperties.getVersion())
                        .description("REST API to send emails")
                        .contact(new Contact().url("https://github.com/devatherock").name("devatherock"))
                        .license(new License().name("MIT")));
    }
}
