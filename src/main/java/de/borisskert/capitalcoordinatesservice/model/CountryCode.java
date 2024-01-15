package de.borisskert.capitalcoordinatesservice.model;

public record CountryCode(String value) {
    public static CountryCode from(String countryCode) {
        return new CountryCode(countryCode);
    }
}
