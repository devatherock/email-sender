package io.github.devatherock.emailsender.util;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;

import lombok.experimental.UtilityClass;

/**
 * Registers mail content handlers. Needed for native image. Based on <a href=
 * "https://github.com/quarkusio/quarkus/issues/1840#issuecomment-479860430">this
 * github comment</a>
 */
@UtilityClass
public class MailUtil {

    public static void init() {
        MailcapCommandMap defaultCommandMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        defaultCommandMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        defaultCommandMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        defaultCommandMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        defaultCommandMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        defaultCommandMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");

        CommandMap.setDefaultCommandMap(defaultCommandMap);
    }
}
