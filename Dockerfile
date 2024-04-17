FROM openjdk:21
EXPOSE 8080
ARG JAR_FILE=target/run-api-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]