package de.borisskert.capitalcoordinatesservice.service;

import de.borisskert.capitalcoordinatesservice.client.CityLocationClient;
import de.borisskert.capitalcoordinatesservice.client.CountryInfoClient;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import de.borisskert.capitalcoordinatesservice.model.CityWithLocation;
import de.borisskert.capitalcoordinatesservice.model.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CapitalCoordinatesService {
    private final CountryInfoClient countryInfoClient;
    private final CityLocationClient cityLocationClient;

    @Autowired
    public CapitalCoordinatesService(CountryInfoClient countryInfoClient, CityLocationClient cityLocationClient) {
        this.countryInfoClient = countryInfoClient;
        this.cityLocationClient = cityLocationClient;
    }

    public CityWithLocation findBy(CountryCode countryCode) {
        City capitalCity = countryInfoClient.retrieveCapitalCity(countryCode);
        CityLocation cityLocation = cityLocationClient.retrieveLocation(capitalCity);

        return capitalCity.withLocation(cityLocation);
    }
}
