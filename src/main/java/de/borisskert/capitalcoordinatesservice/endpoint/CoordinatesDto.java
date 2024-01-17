package de.borisskert.capitalcoordinatesservice.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CoordinatesDto(
        String capital,
        double latitude,
        double longitude,
        String country,
        @JsonProperty("display_name") String displayName
) {
}
