FROM eclipse-temurin:21.0.1_12-jre-jammy

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="1.1.0"

COPY email-sender-*-boot.jar /emailsender.jar

CMD ["java", "-jar", "/emailsender.jar"]