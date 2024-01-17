package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
class CountryInfoClientTest {

    @Autowired
    private CountryInfoClient client;

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

}
