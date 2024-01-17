package de.borisskert.capitalcoordinatesservice.client;

import com.sun.xml.ws.client.ClientTransportException;
import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.oorsprong.webservices.websamples.countryinfo.CountryInfoServiceSoapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
class CountryInfoClientTest {

    @MockBean
    private CountryInfoServiceSoapType countryInfoServiceSoapType;

    @Autowired
    private CountryInfoClient client;

    @BeforeEach
    void setup() {
        when(countryInfoServiceSoapType.capitalCity("DE")).thenReturn("Berlin");
        when(countryInfoServiceSoapType.capitalCity("XX")).thenReturn("Country not found in the database");
        when(countryInfoServiceSoapType.capitalCity("OFFLINE")).thenThrow(ClientTransportException.class);
    }

    @Test
    void shouldGetBerlinForGermanyCountryCode() {
        City city = client.retrieveCapitalCity(new CountryCode("DE"));
        assertThat(city.name()).isEqualTo("Berlin");
    }

    @Test
    void shouldThrowWhenTryingToFindCapitalOfNotExistingCountry() {
        assertThatThrownBy(() -> {
            client.retrieveCapitalCity(new CountryCode("XX"));
        }).isInstanceOf(CountryInfoClient.CountryNotFoundException.class);
    }

    @Test
    void shouldThrowWhenTryingToFindCapitalOffline() {
        assertThatThrownBy(() -> {
            client.retrieveCapitalCity(new CountryCode("OFFLINE"));
        }).isInstanceOf(CountryInfoClient.ServiceUnavailableException.class);
    }
}
