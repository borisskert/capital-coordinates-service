package de.borisskert.capitalcoordinatesservice.endpoint;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("IT")
public class CoordinatesEndpointIT {

    @BeforeEach
    void setup() {
        setupRestAssured();
    }

    @Test
    void shouldReturnBerlinWhenRequestingCapitalsCoordinatesForDe() throws Exception {
        given()
                .auth().basic("test_user_IT", "SvRYuE7VqT5tb3LC6DanWB")

                .when()
                .get("/coordinates/DE")

                .then()
                .statusCode(200)
                .body("capital", equalTo("Berlin"),
                        "latitude", equalTo(52.5170365),
                        "longitude", equalTo(13.3888599),
                        "country", equalTo("Deutschland"),
                        "display_name", equalTo("Berlin, Deutschland")
                );
    }

    @Test
    void shouldReturnZagrebWhenRequestingCapitalsCoordinatesForHr() throws Exception {
        given()
                .auth().basic("test_user_IT", "SvRYuE7VqT5tb3LC6DanWB")

                .when()
                .get("/coordinates/HR")

                .then()
                .statusCode(200)
                .body("capital", equalTo("Zagreb"),
                        "latitude", equalTo(45.8130967),
                        "longitude", equalTo(15.9772795),
                        "country", equalTo("Kroatien"),
                        "display_name", equalTo("Stadt Zagreb, Kroatien")
                );
    }

    @Test
    void shouldReturnBadRequestForFormerYugoslavia() throws Exception {
        given()
                .auth().basic("test_user_IT", "SvRYuE7VqT5tb3LC6DanWB")

                .when()
                .get("/coordinates/YU")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnBadRequestForIllegalCountryCoide() throws Exception {
        given()
                .auth().basic("test_user_IT", "SvRYuE7VqT5tb3LC6DanWB")

                .when()
                .get("/coordinates/XX")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnBadRequestWhenCountryCodeIsMissing() throws Exception {
        given()
                .auth().basic("test_user_IT", "SvRYuE7VqT5tb3LC6DanWB")

                .when()
                .get("/coordinates")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldRespondUnauthorizedWhenBasicAuthIsMissing() throws Exception {
        when()
                .get("/coordinates/DE")

                .then()
                .statusCode(401);
    }

    /**
     * See: <a href="https://stackoverflow.com/a/64339644/13213024">Answer to "How to compare\assert double values in rest assured"</a>
     */
    private static void setupRestAssured() {
        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);

        RestAssured.config = RestAssured.config()
                .jsonConfig(jsonConfig);
    }
}
