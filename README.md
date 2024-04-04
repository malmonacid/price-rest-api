# price-rest-api

API Rest that provides endpoint to consult products rates by dates

Table content
--------------

<!-- @import "[TOC]" (cmd="toc" depthFrom=1 depthTo=5 orderedList=false) -->
<!-- code_chunk_output -->

- [Technologies](#technologies)
- [Modules](#modules)
- [Console Run](#console-run)
- [Intellij Run](#intellij-run)
- [Openapi code generator](#openapi-code-generator)
- [Swagger config](#swagger-config)
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

## swagger-config

1. Swagger definition: http://localhost:8080/swagger-ui/index.html (run project first)
1. Plugin can be configurated

        <configuration> 
        <apiDocsUrl>http://localhost:8080/v3/api-docs</apiDocsUrl> 
        <outputFileName>openapi.json</outputFileName> 
        <outputDir>${project.build.directory}</outputDir> 
        </configuration>

1. openapi file declaration price-rest-api.yml in /resources/spec/ folder
1. if any changes use openapi code generator to regenerate model classes

## openapi-code-generator

            <build>
              <plugins>
                  <plugin>
                      <groupId>org.openapitools</groupId>
                      <artifactId>openapi-generator-maven-plugin</artifactId>
                      <version>6.2.1</version>
                      <executions>
                          <execution>
                              <goals>
                                  <goal>generate</goal>
                              </goals>
                              <configuration>
                                  <skipValidateSpec>true</skipValidateSpec>
                                  <inputSpec>${project.basedir}/src/main/resources/spec/price-rest-api.yaml</inputSpec>
                                  <generatorName>spring</generatorName>
                                  <configOptions>
                                      <openApiNullable>false</openApiNullable>
                                      <interfaceOnly>true</interfaceOnly>
                                  </configOptions>
                              </configuration>
                          </execution>
                      </executions>
                  </plugin>
              </plugins>
          </build>

1. Compile application (mvn clean install) and generate classes in target generated classes folder.

## application-local content

    DEPLOY_ENV: local
    PRICE_RES_API_ADRRESS: localhost
    PASSWORD_H2: password
    USER_H2: sa

## h2-editor

* H2 data definition in resources\data.sql and schema.sql
* after starting the application, we can navigate to http://localhost:8080/h2-console, which will present us with a
  login page.

## Docker

1. Build image

* ./mvnw spring-boot:build-image