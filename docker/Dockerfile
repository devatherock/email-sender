FROM adoptopenjdk/openjdk11:alpine-jre

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="0.1.0"

COPY email-sender-*.jar /emailsender.jar

CMD ["java", "-jar", "/emailsender.jar"]