package de.borisskert.capitalcoordinatesservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CapitalWithCoordinates(
        String capital,
        Double latitude,
        Double longitude,
        String country,
        @JsonProperty("display_name") String displayName
) {
}
