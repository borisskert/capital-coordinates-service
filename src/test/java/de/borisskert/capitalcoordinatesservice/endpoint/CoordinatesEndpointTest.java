package de.borisskert.capitalcoordinatesservice.endpoint;

import de.borisskert.capitalcoordinatesservice.model.CapitalWithCoordinates;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import de.borisskert.capitalcoordinatesservice.service.CapitalCoordinatesService;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CoordinatesEndpointTest {

    @MockBean
    CapitalCoordinatesService mockedService;

    @BeforeEach
    void setup() {
        setupRestAssured();
        setupServiceMock();
    }

    @Test
    void shouldReturnBerlinWhenRequestingCapitalsCoordinatesForDe() throws Exception {
        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates/DE")

                .then()
                .statusCode(200)
                .body("capital", equalTo("Berlin"),
                        "latitude", equalTo(52.5170365),
                        "longitude", equalTo(13.3888599),
                        "country", equalTo("Deutschland"),
                        "display_name", equalTo("Berlin, Deutschland"));
    }

    @Test
    void shouldReturnUnauthorizedWhenRequestingWithoutAuthentication() {
        when()
                .get("/coordinates/DE")

                .then()
                .statusCode(401);
    }

    @Test
    void shouldReturnBadRequestWhenRequestingForIllegalCountryCode() {
        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates/XX")

                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnBadRequestWhenCountryCodeIsMissing() {
        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates")

                .then()
                .statusCode(400);
    }

    private void setupServiceMock() {
        CountryCode deutschland = CountryCode.from("DE");
        CapitalWithCoordinates berlin = new CapitalWithCoordinates(
                "Berlin",
                52.5170365,
                13.3888599,
                "Deutschland",
                "Berlin, Deutschland"
        );

        Mockito.when(mockedService.findBy(deutschland)).thenReturn(berlin);
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
