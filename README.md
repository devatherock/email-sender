[![CircleCI](https://circleci.com/gh/devatherock/email-sender.svg?style=svg)](https://circleci.com/gh/devatherock/email-sender)
[![Docker Pulls](https://img.shields.io/docker/pulls/devatherock/email-sender.svg)](https://hub.docker.com/r/devatherock/email-sender/)
[![Docker Image Size](https://img.shields.io/docker/image-size/devatherock/email-sender.svg?sort=date)](https://hub.docker.com/r/devatherock/email-sender/)
[![Docker Image Layers](https://img.shields.io/microbadger/layers/devatherock/email-sender.svg)](https://microbadger.com/images/devatherock/email-sender)
# email-sender
REST API to send emails. Sends the email using the configured SMTP server.

## API Reference
Refer the swagger [spec](https://smtp-email-sender-api.herokuapp.com/swagger/v3/api-docs) or the swagger 
[UI](https://smtp-email-sender-api.herokuapp.com/swagger-ui.html)

### Sample YAML request payload
```yaml
to:
 - email: test.email@mailinator.com
subject: Test email
html: |
  <!DOCTYPE html>
  <html>
    <body>
       <h3>Hello, World!</h3>
    </body>
  </html>
text: This is a test email
```

## Configurable properties

| Name                                  |   Type       |   Default        |   Description                                                                     |
|---------------------------------------|--------------|------------------|-----------------------------------------------------------------------------------|
| emailsender.smtp.embedded             |   boolean    |   false          |   Indicates if an embedded SMTP server should be used. Turned off by default      |
| emailsender.smtp.username             |   String     |   (None)         |   The SMTP username                                                               |
| emailsender.smtp.password             |   String     |   (None)         |   The SMTP password                                                               |
| emailsender.smtp.host                 |   String     |   (None)         |   The SMTP server name                                                            |
| emailsender.smtp.port                 |   int        |   587            |   The SMTP port. Defaults to 587                                                  |
| emailsender.smtp.from-address.name    |   String     |   (None)         |   The from name to use in outbound emails                                         |
| emailsender.smtp.from-address.email   |   String     |   (None)         |   The from email address to use in outbound emails                                |