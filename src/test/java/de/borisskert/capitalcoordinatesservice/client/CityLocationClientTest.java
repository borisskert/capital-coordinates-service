package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import de.borisskert.capitalcoordinatesservice.model.GpsLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
class CityLocationClientTest {

    @Autowired
    private CityLocationClient client;

    @Test
    void shouldRetrieveLocationForBerlin() {
        final CityLocation location = client.getLocation(City.of("Berlin"));
        assertEquals(new GpsLocation(52.5170365, 13.3888599).toCityLocation("Deutschland", "Berlin, Deutschland"), location);
    }

    @Test
    void shouldRetrieveLocationForZagreb() {
        final CityLocation location = client.getLocation(City.of("Zagreb"));
        assertEquals(new GpsLocation(45.8130967, 15.9772795).toCityLocation("Kroatien", "Stadt Zagreb, Kroatien"), location);
    }

    @Test
    void shouldThrowWhenCityNotFound() {
        assertThrows(CityLocationClient.CityNotFoundException.class, () -> {
            client.getLocation(City.of("NotExistingCity"));
        });
    }
}
