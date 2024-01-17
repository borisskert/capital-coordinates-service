package de.borisskert.capitalcoordinatesservice.client;

import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.oorsprong.webservices.websamples.countryinfo.CountryInfoServiceSoapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryInfoClient {
    private static final String COUNTRY_NOT_FOUND = "Country not found in the database";

    private final CountryInfoServiceSoapType countryInfoServiceSoapType;

    @Autowired
    public CountryInfoClient(CountryInfoServiceSoapType countryInfoServiceSoapType) {
        this.countryInfoServiceSoapType = countryInfoServiceSoapType;
    }

    public City retrieveCapitalCity(CountryCode countryCode) {
        String capitalCity = countryInfoServiceSoapType.capitalCity(countryCode.value());

        // SOAP service does not throw an exception nor return null when country code is not found - it returns a string
        if (capitalCity.equals(COUNTRY_NOT_FOUND)) {
            throw new CountryNotFoundException(
                    "Could not find capital city for country code '" + countryCode.value() + "'"
            );
        }

        return new City(capitalCity);
    }

    public static class CountryNotFoundException extends RuntimeException {
        public CountryNotFoundException(String message) {
            super(message);
        }
    }
}
