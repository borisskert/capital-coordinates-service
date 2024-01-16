package de.borisskert.capitalcoordinatesservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CityWithLocation(
        String capital,
        Double latitude,
        Double longitude,
        String country,
        @JsonProperty("display_name") String displayName
) {
}
