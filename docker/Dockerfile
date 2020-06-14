FROM adoptopenjdk/openjdk11:alpine-jre

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="0.1.0"

COPY email-sender-api-*.jar /emailsenderapi.jar

CMD ["java", "-jar", "/emailsenderapi.jar"]