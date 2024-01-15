package de.borisskert.capitalcoordinatesservice.model;

public record CapitalCity(String name) {
    public static CapitalCity from(String name) {
        return new CapitalCity(name);
    }
}
