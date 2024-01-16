package de.borisskert.capitalcoordinatesservice.model;

public record City(String name) {
    public static City of(String name) {
        return new City(name);
    }

    public CityWithLocation withLocation(CityLocation location) {
        return new CityWithLocation(
                this.name(),
                location.gpsLocation().latitude(),
                location.gpsLocation().longitude(),
                location.country(),
                location.displayName()
        );
    }
}
