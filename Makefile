docker_tag=latest
all=false

clean:
	./gradlew clean
check:
ifeq ($(all), true)
	yamllint -d relaxed . --no-warnings
endif
	./gradlew check
fast-build:
	./gradlew build -x dependencyCheckAggregate	
integration-test:
	docker-compose up &
	./gradlew integrationTest --tests '*ControllerIntegrationSpec*'
	docker-compose down
embedded-integration-test:
	docker-compose -f docker-compose-embedded.yml up &
	./gradlew integrationTest --tests '*EmbeddedIntegrationSpec*'
	docker-compose -f docker-compose-embedded.yml down	
docker-build:
	./gradlew clean build
	cp build/libs/email-sender-*.jar docker/
	docker build -t devatherock/email-sender:$(docker_tag) docker