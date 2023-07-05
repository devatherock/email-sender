docker_tag=latest
all=false

clean:
	./gradlew clean
check:
ifeq ($(all), true)
	yamllint -d relaxed . --no-warnings
endif
	./gradlew check	
integration-test:
	docker-compose up &
	./gradlew integrationTest
	docker-compose down
docker-build:
	./gradlew clean build
	cp build/libs/email-sender-*.jar docker/
	docker build -t devatherock/email-sender:$(docker_tag) docker