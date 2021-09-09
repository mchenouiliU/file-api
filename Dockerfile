FROM adoptopenjdk/openjdk11:latest
ARG JAR_NAME=file-api-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=build/libs/$JAR_NAME
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]