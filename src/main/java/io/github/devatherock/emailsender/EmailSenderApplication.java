package io.github.devatherock.emailsender;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.devatherock.emailsender.util.YamlHttpMessageConverter;

import java.util.List;

/***
 * Main class for the application
 *
 * @author devatherock
 **/
@SpringBootApplication
public class EmailSenderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EmailSenderApplication.class).bannerMode(Mode.LOG).run(args);
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
