package de.borisskert.capitalcoordinatesservice.endpoint;

import de.borisskert.capitalcoordinatesservice.client.CityLocationClient;
import de.borisskert.capitalcoordinatesservice.client.CountryInfoClient;
import de.borisskert.capitalcoordinatesservice.model.CityWithLocation;
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
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class CoordinatesEndpointTest {

    @MockBean
    CapitalCoordinatesService mockedService;

    @BeforeEach
    void setup() {
        setupRestAssured();
    }

    @Test
    void shouldReturnBerlinWhenRequestingCapitalsCoordinatesForDe() throws Exception {
        setupMocksForBerlin();

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
    void shouldRespondBadRequestWhenRequestingCapitalsCoordinatesForFormerYugoslaviaWhichCannotBeFound() throws Exception {
        setupMocksForCountryNotFound();

        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates/YU")

                .then()
                .statusCode(400);
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

    @Test
    void shouldRespondTemporaryUnavailableWhenCountryInfoServiceUnavailable() {
        setupMocksForCountryInfoServiceUnavailable();

        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates/DE")

                .then()
                .statusCode(503);
    }

    @Test
    void shouldRespondTemporaryUnavailableWhenCityLocationServiceUnavailable() {
        setupMocksForCityLocationServiceUnavailable();

        given()
                .auth().basic("test_user", "test_password123")

                .when()
                .get("/coordinates/DE")

                .then()
                .statusCode(503);
    }

    private void setupMocksForBerlin() {
        CountryCode deutschland = CountryCode.from("DE");
        CityWithLocation berlin = new CityWithLocation(
                "Berlin",
                52.5170365,
                13.3888599,
                "Deutschland",
                "Berlin, Deutschland"
        );

        Mockito.when(mockedService.findBy(deutschland)).thenReturn(berlin);
    }

    private void setupMocksForCountryNotFound() {
        CountryCode formerYugoslavia = CountryCode.from("YU");
        Mockito.when(mockedService.findBy(formerYugoslavia)).thenThrow(CountryInfoClient.CountryNotFoundException.class);
    }

    private void setupMocksForCountryInfoServiceUnavailable() {
        CountryCode deutschland = CountryCode.from("DE");
        Mockito.when(mockedService.findBy(deutschland)).thenThrow(CountryInfoClient.ServiceUnavailableException.class);
    }

    private void setupMocksForCityLocationServiceUnavailable() {
        CountryCode deutschland = CountryCode.from("DE");
        CityWithLocation berlin = new CityWithLocation(
                "Berlin",
                52.5170365,
                13.3888599,
                "Deutschland",
                "Berlin, Deutschland"
        );

        Mockito.when(mockedService.findBy(deutschland)).thenThrow(CityLocationClient.ServiceUnavailableException.class);
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
