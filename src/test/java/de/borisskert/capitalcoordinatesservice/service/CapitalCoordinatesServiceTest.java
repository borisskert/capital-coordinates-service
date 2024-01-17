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
        setupMockForBerlin();
    }

    @Test
    void shouldShouldReturnBerlinsLocationForGermanysCode() {
        CityWithLocation cityWithLocation = service.findBy(germany);

        assertThat(cityWithLocation.capital()).isEqualTo("Berlin");
        assertThat(cityWithLocation.latitude()).isEqualTo(52.5170365);
        assertThat(cityWithLocation.longitude()).isEqualTo(13.3888599);
        assertThat(cityWithLocation.country()).isEqualTo("Germany");
        assertThat(cityWithLocation.displayName()).isEqualTo("Berlin, Deutschland");
    }

    private void setupMockForBerlin() {
        City berlin = City.of("Berlin");

        Mockito.when(mockedCountryInfoClient.retrieveCapitalCity(germany)).thenReturn(berlin);

        CityLocation berlinsLocation = new GpsLocation(52.5170365, 13.3888599)
                .toCityLocation("Germany", "Berlin, Deutschland");

        Mockito.when(mockedCityLocationClient.retrieveLocation(berlin)).thenReturn(berlinsLocation);
    }
}
