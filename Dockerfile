FROM openjdk:17-jdk-alpine

MAINTAINER https://github.com/borisskert/spring-static-web-content-example

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY target/capital-coordinates-service.jar app.jar
COPY docker/start.sh start.sh

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["./start.sh"]
