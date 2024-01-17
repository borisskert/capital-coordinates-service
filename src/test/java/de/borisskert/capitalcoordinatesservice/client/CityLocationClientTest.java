package de.borisskert.capitalcoordinatesservice.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.WireMockInitializer;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {WireMockInitializer.class})
class CityLocationClientTest {

    @Autowired
    private CityLocationClient client;

    @Autowired
    private WireMockServer wiremock;

    @Test
    void shouldRetrieveLocationForBerlin() {
        wiremock.start();

        final CityLocation location = client.retrieveLocation(City.of("Berlin"));

        assertThat(location.gpsLocation().latitude()).isEqualTo(52.5170365);
        assertThat(location.gpsLocation().longitude()).isEqualTo(13.3888599);
        assertThat(location.displayName()).isEqualTo("Berlin, Deutschland");
        assertThat(location.country()).isEqualTo("Deutschland");
    }

    @Test
    void shouldRetrieveLocationForZagreb() {
        final CityLocation location = client.retrieveLocation(City.of("Zagreb"));

        assertThat(location.gpsLocation().latitude()).isEqualTo(45.8130967);
        assertThat(location.gpsLocation().longitude()).isEqualTo(15.9772795);
        assertThat(location.displayName()).isEqualTo("Stadt Zagreb, Kroatien");
        assertThat(location.country()).isEqualTo("Kroatien");
    }

    @Test
    void shouldThrowWhenCityNotFound() {
        assertThrows(CityLocationClient.CityNotFoundException.class, () -> {
            client.retrieveLocation(City.of("NotExistingCity"));
        });
    }
}
