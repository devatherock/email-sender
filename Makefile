.PHONY: build
docker_tag=latest
all=false

clean:
	rm -rf build
check:
ifeq ($(all), true)
	yamllint -d relaxed . --no-warnings
endif
	./gradlew check
build:
	./gradlew build
integration-test:
	rm -rf logs-intg.txt
	docker compose up --wait
	docker logs -f email-sender-intg > logs-intg.txt &
	./gradlew integrationTest --tests '*ControllerIntegrationSpec*'
	docker-compose down
embedded-integration-test:
	rm -rf logs-intg-embedded.txt
	docker compose -f docker-compose-embedded.yml up --wait
	docker logs -f email-sender-intg-embedded > logs-intg-embedded.txt &
	./gradlew integrationTest --tests '*EmbeddedIntegrationSpec*'
	docker-compose -f docker-compose-embedded.yml down
docker-build:
	docker build --progress=plain \
	  --build-arg GRADLE_ARGS='-Dnative.threads=2 -Dnative.xmx=3072m -Dnative.arch=native -Dnative.mode=dev' \
	  -t devatherock/email-sender:$(docker_tag) -f docker/Dockerfile .
