package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.CapitalCity;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import de.borisskert.capitalcoordinatesservice.model.GpsLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceApplication.class)
class CityLocationClientTest {

    @Autowired
    private CityLocationClient client;

    @Test
    void shouldRetrieveLocationForBerlin() {
        final CityLocation location = client.getCoordinates(CapitalCity.from("Berlin"));
        assertEquals(new GpsLocation(52.5170365, 13.3888599).toCityLocation("Berlin, Deutschland"), location);
    }

    @Test
    void shouldThrowWhenCityNotFound() {
        assertThrows(CityLocationClient.CityNotFoundException.class, () -> {
            client.getCoordinates(CapitalCity.from("NotExistingCity"));
        });
    }
}
