package de.borisskert.capitalcoordinatesservice.model;

import java.util.Objects;

public record CityLocation(GpsLocation gpsLocation, String country, String displayName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityLocation that = (CityLocation) o;
        return Objects.equals(gpsLocation, that.gpsLocation) && Objects.equals(country, that.country) && Objects.equals(displayName, that.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gpsLocation, country, displayName);
    }
}
