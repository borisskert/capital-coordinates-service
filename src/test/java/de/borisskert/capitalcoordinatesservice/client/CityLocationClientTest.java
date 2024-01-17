package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
class CityLocationClientTest {

    @Autowired
    private CityLocationClient client;

    @Test
    void shouldRetrieveLocationForBerlin() {
        final CityLocation location = client.retrieveLocation(City.of("Berlin"));

        assertThat(location.gpsLocation().latitude()).isCloseTo(52.5, offset(0.1));
        assertThat(location.gpsLocation().longitude()).isCloseTo(13.3, offset(0.1));
        assertThat(location.displayName()).isEqualTo("Berlin, Deutschland");
        assertThat(location.country()).isEqualTo("Deutschland");
    }

    @Test
    void shouldRetrieveLocationForZagreb() {
        final CityLocation location = client.retrieveLocation(City.of("Zagreb"));

        assertThat(location.gpsLocation().latitude()).isCloseTo(45.8, offset(0.1));
        assertThat(location.gpsLocation().longitude()).isCloseTo(15.9, offset(0.1));

        // Sometimes the backend returns "Stadt Zagreb, Kroatien" and sometimes "Zagreb, Kroatien"
        assertThat(location.displayName()).contains("Stadt Zagreb, Kroatien");

        assertThat(location.country()).isEqualTo("Kroatien");
    }

    @Test
    void shouldThrowWhenCityNotFound() {
        assertThrows(CityLocationClient.CityNotFoundException.class, () -> {
            client.retrieveLocation(City.of("NotExistingCity"));
        });
    }
}
