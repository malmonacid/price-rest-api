FROM openjdk:21-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/price-rest-api-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]