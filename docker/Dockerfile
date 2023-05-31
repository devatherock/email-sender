FROM eclipse-temurin:17.0.7_7-jre-jammy

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="0.3.0"

COPY email-sender-*-boot.jar /emailsender.jar

CMD ["java", "-jar", "/emailsender.jar"]