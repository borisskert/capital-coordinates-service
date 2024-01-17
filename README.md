# Task Data Transfer

This micro service provides a REST web service that calls a SOAP web service, transforms
the data from the XML to a JSON, sends this JSON to a REST web service and returns the
result as response.

## Build project as jar package

To build the project, run the following command:

```bash
mvn package
```

## Run unit tests

To run the unit tests, run the following command:

```bash
mvn test
```

## Run integration tests

To run the integration tests, run the following command:

```bash
mvn verify
```

## Run project

To run the project (after packaging), run the following command:

```bash
java -jar target/capital-coordinates-service.jar --spring.security.user.name=USERNAME --spring.security.user.password=PASSWORD
```

Alternatively, you can build and run the project by Docker with the following command:

```bash
docker compose up --build
```

In this case you have to set the username and password in the `docker-compose.yml` file or use the default values.

## REST API

To have a look at the REST API, run the project and open the following URL in your browser:

```bash
http://localhost:8080/swagger-ui.html
```

## Design decisions

- The API prefix "/v1" can be set by the SpringBoot Configuration `server.servlet.context-path=/v1` at the deployment of
  the App. This can be tested easily in Docker environment by setting the property `SERVER_SERVLET_CONTEXT_PATH=/v1` in
  the `environment` section.
  Please note that the Swagger-UI also will be moved into the same context path,
  i.e.: http://localhost:8080/v1/swagger-ui/index.html
- By default, no username nor password for Basic Auth are configured in application.yml. Spring Boot has its own
  mechanism to provide a generated password instead which will be printed in the console-log at the start of the
  application. SpringBoot's default username is `user`.
  To overwrite this mechanics use the properties `spring.security.user.name` and `spring.security.user.password`
- To communicate with the SOAP web service, I use the maven-plugin `jaxws-maven-plugin` to generate the Java classes
  from the WSDL file. The generated classes are located in the
  package `org.oorsprong.webservices.websamples.countryinfo`.
- I had to modify the URL to retrieve the coordinates for a city via REST API to get a German display name for the
  cities.
- The microservice will respond with HTTP 503 if the SOAP web service or the REST web service are not available or the
  connection is broken.
- Internal Server Errors (HTTP 500) are not handled and should not occur.
