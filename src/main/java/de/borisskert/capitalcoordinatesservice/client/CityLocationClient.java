package de.borisskert.capitalcoordinatesservice.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.borisskert.capitalcoordinatesservice.model.City;
import de.borisskert.capitalcoordinatesservice.model.CityLocation;
import de.borisskert.capitalcoordinatesservice.model.GpsLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CityLocationClient {

    private final RestClient restClient;
    private final String url;

    @Autowired
    public CityLocationClient(
            RestClient restClient,
            @Value("${capital-coordinates-service.city-location-client.url}") String url
    ) {
        this.restClient = restClient;
        this.url = url;
    }

    public CityLocation retrieveLocation(City city) {
        List<OpenStreetMapCityResponse> response = restClient.get()
                .uri(url, city.name())
                .retrieve()
                .body(new ParameterizedTypeReference<List<OpenStreetMapCityResponse>>() {
                });

        if (response.isEmpty()) {
            throw new CityNotFoundException("Could not find coordinates for capital city '" + city.name() + "'");
        }

        return response.get(0).toCityLocation();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenStreetMapCityResponse {
        public Double lat;
        public Double lon;
        public Address address;
        public String display_name;

        private GpsLocation toGpsLocation() {
            return new GpsLocation(lat, lon);
        }

        public CityLocation toCityLocation() {
            return new CityLocation(toGpsLocation(), address.country, display_name);
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Address {
            public String country;
        }
    }

    public static class CityNotFoundException extends RuntimeException {
        public CityNotFoundException(String message) {
            super(message);
        }
    }
}
