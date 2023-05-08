DOCKER_TAG=latest

clean:
	./gradlew clean
integration-test:
	docker-compose up &
	./gradlew integrationTest
	docker-compose down
docker-build:
	./gradlew clean build
	cp build/libs/email-sender-*.jar docker/
	docker build -t devatherock/email-sender:$(DOCKER_TAG) docker