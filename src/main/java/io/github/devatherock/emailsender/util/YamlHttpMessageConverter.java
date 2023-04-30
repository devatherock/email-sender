package io.github.devatherock.emailsender.util;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.yaml.snakeyaml.Yaml;

/**
 * {@link org.springframework.http.converter.HttpMessageConverter} to
 * produce/consume YAML
 *
 * @param <T>
 */
public class YamlHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {
    /**
     * The supported content type is {@code application/x-yaml}
     */
    public YamlHttpMessageConverter() {
        super(new MediaType("application", "x-yaml"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return new Yaml().loadAs(inputMessage.getBody(), clazz);
    }

    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody())) {
            new Yaml().dump(t, writer);
        }
    }
}