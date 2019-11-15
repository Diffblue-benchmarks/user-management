FROM adoptopenjdk/openjdk11:alpine-slim

VOLUME /tmp

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

EXPOSE 8080