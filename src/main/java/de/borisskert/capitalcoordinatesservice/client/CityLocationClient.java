package de.borisskert.capitalcoordinatesservice.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.borisskert.capitalcoordinatesservice.model.CapitalCity;
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

    public CityLocation getCoordinates(CapitalCity capitalCity) {
        List<OpenStreetMapCityResponse> response = restClient.get()
                .uri(url, capitalCity.name())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response.isEmpty()) {
            throw new CityNotFoundException("Could not find coordinates for capital city '" + capitalCity.name() + "'");
        }

        return response.get(0).toCityLocation();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenStreetMapCityResponse {
        private final Double lat;
        private final Double lon;
        private final String display_name;

        public OpenStreetMapCityResponse(double lat, double lon, String display_name) {
            this.lat = lat;
            this.lon = lon;
            this.display_name = display_name;
        }

        private GpsLocation toGpsLocation() {
            return new GpsLocation(lat, lon);
        }

        public CityLocation toCityLocation() {
            return new CityLocation(toGpsLocation(), display_name);
        }
    }

    public static class CityNotFoundException extends RuntimeException {
        public CityNotFoundException(String message) {
            super(message);
        }
    }
}
