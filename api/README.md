# api

Price Rest Api module api. Contains api definition and generate api

## Technologies

* Java 21
* Spring Boot 3.2.3
* Maven version 3.9.3

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
