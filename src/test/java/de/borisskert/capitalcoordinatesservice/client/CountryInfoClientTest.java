package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.ServiceApplication;
import de.borisskert.capitalcoordinatesservice.model.CapitalCity;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceApplication.class)
class CountryInfoClientTest {

    @Autowired
    private CountryInfoClient client;

    @Test
    void shouldGetBerlinForGermanyCountryCode() {
        CapitalCity capitalCity = client.getCapitalCity(new CountryCode("DE"));
        assertEquals("Berlin", capitalCity.name());
    }

    @Test
    void shouldThrowWhenTryingToFindCapitalOfNotExistingCountry() {
        assertThrows(CountryInfoClient.CountryNotFoundException.class, () -> {
            client.getCapitalCity(new CountryCode("XX"));
        });
    }

}
