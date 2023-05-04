# Changelog

## [Unreleased]
### Added
- [#6](https://github.com/devatherock/email-sender/issues/6): Unit tests
- Dependency check plugin

### Changed
- Upgraded gradle to `7.6.1`
- Upgraded spring boot to `3.0.6`
- Upgraded snakeyaml to `2.0`
- Upgraded groovy to `4.0.11`
- Upgraded java to `17`

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