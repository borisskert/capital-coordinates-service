FROM openjdk:17-jdk-alpine as build

MAINTAINER https://github.com/borisskert/capital-coordinates-service

WORKDIR /usr/local/src/capital-coordinates-service

COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY --from=build /usr/local/src/capital-coordinates-service/target/capital-coordinates-service.jar app.jar
COPY --from=build /usr/local/src/capital-coordinates-service/docker/start.sh start.sh

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["./start.sh"]
