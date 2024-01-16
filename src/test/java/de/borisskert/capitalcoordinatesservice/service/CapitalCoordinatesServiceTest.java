package de.borisskert.capitalcoordinatesservice.service;

import de.borisskert.capitalcoordinatesservice.client.CityLocationClient;
import de.borisskert.capitalcoordinatesservice.client.CountryInfoClient;
import de.borisskert.capitalcoordinatesservice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CapitalCoordinatesServiceTest {
    private final CountryCode germany = CountryCode.from("DE");

    @MockBean
    private CountryInfoClient mockedCountryInfoClient;

    @MockBean
    private CityLocationClient mockedCityLocationClient;

    @Autowired
    private CapitalCoordinatesService service;

    @BeforeEach
    public void setup() {
        City berlin = City.of("Berlin");

        Mockito.when(mockedCountryInfoClient.getCapitalCity(germany)).thenReturn(berlin);

        CityLocation berlinsLocation = new GpsLocation(52.5170365, 13.3888599)
                .toCityLocation("Germany", "Berlin, Deutschland");

        Mockito.when(mockedCityLocationClient.getLocation(berlin)).thenReturn(berlinsLocation);
    }

    @Test
    void shouldShouldReturnBerlinsLocationForGermanysCode() {
        CityWithLocation cityWithLocation = service.findBy(germany);

        assertThat(cityWithLocation.capital()).isEqualTo("Berlin");
        assertThat(cityWithLocation.latitude()).isCloseTo(52.5, offset(0.1));
        assertThat(cityWithLocation.longitude()).isEqualTo(13.3, offset(0.1));
        assertThat(cityWithLocation.country()).isEqualTo("Germany");
        assertThat(cityWithLocation.displayName()).isEqualTo("Berlin, Deutschland");
    }
}
