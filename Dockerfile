FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker
ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]