FROM eclipse-temurin:17.0.11_9-jre-jammy

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="1.1.0"

COPY email-sender-*-boot.jar /emailsender.jar

CMD ["java", "-jar", "/emailsender.jar"]