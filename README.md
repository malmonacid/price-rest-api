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
- [H2 Editor](#h2-editor)

<!-- /code_chunk_output -->

## Technologies

* Java 21
* Spring Boot 3.2.3
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

1. Swagger definition: http://localhost:8080/swagger-ui/index.html
1. Plugin can be configurated

        <configuration> 
        <apiDocsUrl>http://localhost:8080/v3/api-docs</apiDocsUrl> 
        <outputFileName>openapi.json</outputFileName> 
        <outputDir>${project.build.directory}</outputDir> 
        </configuration>

1. openapi file declaration price-rest-api.yml in /resources/spec/ folder
1. if any changes use openapi code generator to regenerate model classes

## openapi-code-generator

            <plugin>
                <groupId>org.openapitools</groupId>
                <artifactId>openapi-generator-maven-plugin</artifactId>
                <version>5.3.1</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <configuration>
                            <verbose>true</verbose>
                            <inputSpec>${project.basedir}/src/main/resources/spec/price-rest-api.yml</inputSpec>
                            <generatorName>spring</generatorName>
                            <library>spring-boot</library>
                            <apiPackage>${default.package}.handler</apiPackage>
                            <modelPackage>${default.package}.model</modelPackage>
                            <invokerPackage>${default.package}.handler</invokerPackage>
                            <configOptions>
                                <sourceFolder>src/main/java</sourceFolder>
                                <delegatePattern>true</delegatePattern>
                                <interfaceOnly>false</interfaceOnly>
                                <!-- Skip - only needed model generated files -->
                                <skipDefaultInterface>true</skipDefaultInterface>
                            </configOptions>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

1. Add openapi builder to de pom plugins
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

