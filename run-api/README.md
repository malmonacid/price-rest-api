# run-api

Price Api Rest module for boot application.

Table content
--------------

<!-- @import "[TOC]" (cmd="toc" depthFrom=1 depthTo=5 orderedList=false) -->
<!-- code_chunk_output -->

- [Technologies](#technologies)
- [Modules](#modules)
- [Console Run](#console-run)
- [Intellij Run](#intellij-run)
- [application-local.yml content](#application-local-content)
- [H2 Editor](#h2-editor)
- [Docker](#Docker)

<!-- /code_chunk_output -->

## Technologies

* Java 21
* Spring Boot 3.2.3
* Maven version 3.9.3
* JUnit Jupiter

## Modules

* api
* application
* domain
* infrastructure
* run-api

## Console Run

1. Create file application-local.yml in /src/main/resources, and add the secrets that are missing.
1. $ mvn clean spring-boot:run -Dspring-boot.run.profiles=local

**WARNING: under no circumstances upload these changes to the repository.**

## Intellij run

1. Create file [application-local.yml content](#application-local-content), and add the secrets that are missing.

1. Set profile active to local -Dspring.profiles.active=local
1. Finally, select the created configuration and click run/debug.

## application-local content

    DEPLOY_ENV: local
    PRICE_RES_API_ADRRESS: localhost
    PASSWORD_H2: password
    USER_H2: sa

## Docker

1. Build image

* docker build -t price-rest-api:latest .