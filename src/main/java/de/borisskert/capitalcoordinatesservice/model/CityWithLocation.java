package de.borisskert.capitalcoordinatesservice.model;

import de.borisskert.capitalcoordinatesservice.endpoint.CoordinatesDto;

public record CityWithLocation(
        String capital,
        Double latitude,
        Double longitude,
        String country,
        String displayName
) {
    public CoordinatesDto toDto() {
        return new CoordinatesDto(
                capital,
                latitude,
                longitude,
                country,
                displayName
        );
    }
}
