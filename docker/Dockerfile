FROM devatherock/graalvm:17-ol8 as graalvm

COPY . /home/app
WORKDIR /home/app

ARG GRADLE_ARGS
RUN ./gradlew nativeCompile ${GRADLE_ARGS}


FROM gcr.io/distroless/base-debian11:latest

LABEL maintainer="devatherock@gmail.com"
LABEL io.github.devatherock.version="2.0.0"

COPY --from=graalvm /home/app/build/native/nativeCompile/email-sender /app/emailsender

ENTRYPOINT ["/app/emailsender"]
