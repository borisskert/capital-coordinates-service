package de.borisskert.capitalcoordinatesservice.model;

import java.util.Objects;

public record GpsLocation(double latitude, double longitude) {

    public CityLocation toCityLocation(String country, String displayName) {
        return new CityLocation(this, country, displayName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GpsLocation location = (GpsLocation) o;
        return Double.compare(latitude, location.latitude) == 0 && Double.compare(longitude, location.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
