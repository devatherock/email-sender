# Changelog

## [Unreleased]
### Added
- Used cache for integration tests
- Integration tested the arm image
- Persisted build reports
- Tests for YAML input
- [#25](https://github.com/devatherock/email-sender/issues/25): Custom error response for validation errors
- [#14](https://github.com/devatherock/email-sender/issues/14): Tests for sends to bcc addresses
- Unit and integration tests for sends using embedded SMTP server

### Changed
- Upgraded `snakeyaml` to `2.2`
- chore: Configure Renovate
- Configure Mend Bolt for GitHub
- fix(deps): update dependency org.simplejavamail:simple-java-mail to `6.7.6`
- chore(deps): update plugin com.diffplug.spotless to v6.25.0
- fix(deps): update dependency org.springdoc:springdoc-openapi-starter-webmvc-ui to v2.5.0
- chore(deps): update cimg/openjdk docker tag to v17.0.11
- chore(deps): update eclipse-temurin docker tag to v17.0.11_9-jre-jammy
- chore(deps): update templates orb to v0.7.0
- fix(deps): update dependency jakarta.validation:jakarta.validation-api to v3.1.0
- fix(deps): update dependency org.xerial:sqlite-jdbc to v3.46.0.0
- fix(deps): update dependency org.projectlombok:lombok to v1.18.34
- chore(deps): update plugin io.spring.dependency-management to v1.1.6
- chore(deps): update dependency gradle to v8.9
- chore(deps): update plugin org.springframework.boot to v3.3.4
- chore(deps): update eclipse-temurin docker tag to v17.0.12_7-jre-jammy
- fix(deps): update dependency org.apache.groovy:groovy-json to v4.0.23
- fix(deps): update dependency org.xerial:sqlite-jdbc to v3.46.1.0
- Upgraded `dnsjava` to `3.6.1`
- chore(deps): update dependency gradle to v8.10.1
- fix(deps): update dependency dnsjava:dnsjava to v3.6.2
- fix(deps): update dependency org.springdoc:springdoc-openapi-starter-webmvc-ui to v2.6.0
- fix(deps): update dependency org.yaml:snakeyaml to v2.3
- chore(deps): update dependency gradle to v8.10.2

### Removed
- Dependency check plugin

## [1.0.0] - 2023-05-31
### Added
- [#6](https://github.com/devatherock/email-sender/issues/6): Unit tests
- Dependency check plugin
- Used [Wiser](https://github.com/voodoodyne/subethasmtp/blob/master/Wiser.md) for integration tests
- [#8](https://github.com/devatherock/email-sender/issues/8): Integration tests

### Changed
- Upgraded gradle to `7.6.1` and java to `17`
- Upgraded spring boot to `3.0.6`
- Upgraded snakeyaml to `2.0`
- Upgraded groovy to `4.0.11`
- Updated dockerhub readme in CI pipeline
- Built a multi-arch docker image

## [0.2.0] - 2022-11-04
### Added
- Config required to deploy to `render.com`

### Removed
- Deployment to heroku

## [0.1.2] - 2020-06-14
### Changed
- Enabled `/metrics` endpoint to monitor memory utilization

## [0.1.1] - 2020-06-14
### Changed
- Let emails be sent with from address in request, when using the embedded SMTP server

## [0.1.0] - 2020-06-14
### Added
- Initial version
- Configuration to be able to send email using a non-embedded SMTP server