# price-rest-api

API Rest that provides endpoint to consult products rates by dates

Table content
--------------

<!-- @import "[TOC]" (cmd="toc" depthFrom=1 depthTo=5 orderedList=false) -->
<!-- code_chunk_output -->

- [Technologies](#technologies)
- [Console Run](#console-run)
- [Intellij Run](#intellij-run)
- [Openapi code generator](#openapi-code-generator)
- [Swagger config](#swagger-config)
- [application-local.yml content](#application-local-content)

<!-- /code_chunk_output -->

## Technologies

* Java 21
* Spring Boot 3.2.0
* Maven version 3.9.3
* JUnit Jupiter

## Console Run

1. Create file application-local.yml in /src/main/resources, and add the secrets that are missing.
1. $ mvn clean spring-boot:run -Dspring-boot.run.profiles=local

**WARNING: under no circumstances upload these changes to the repository.**

## Intellij run

1. Create file [application-local.yml content](#application-local-content), and add the secrets that are missing.
1. Run -> Edit Configurations -> Add New Configuration -> Application
1. Add the project main class <<Application.java>> to the configuration and confirm (OK).
1. Set profile active to local -Dspring.profiles.active=local
1. Finally, select the created configuration and click run/debug.

## swagger-config

1. openapi file declaration price-rest-api.yml in /resources/spec/ folder
1. if any changes use openapi code generator to regenerate model classes
1. run application and navigate to http://localhost:8080/swagger-ui/index.html

## openapi-code-generator

1. Add openapi builder to de pom plugins
1. Compile application (mvn clean install) and generate classes in target generated classes folder.

## application-local content

    DEPLOY_ENV: local
    ENVIRONMENT: local
