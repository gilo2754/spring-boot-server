FROM eclipse-temurin:17
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
