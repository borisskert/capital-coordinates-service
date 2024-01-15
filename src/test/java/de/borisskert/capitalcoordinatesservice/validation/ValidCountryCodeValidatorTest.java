package de.borisskert.capitalcoordinatesservice.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidCountryCodeValidatorTest {

    private ValidCountryCodeValidator validator;

    @Test
    void shouldAcceptCodeForGermany() {
        this.validator = new ValidCountryCodeValidator();
        ValidCountryCodeValidator validator = this.validator;
        assertTrue(validator.isValid("DE", null));
    }

    @Test
    void shouldShouldNotAcceptCodeForXX() {
        this.validator = new ValidCountryCodeValidator();
        ValidCountryCodeValidator validator = this.validator;
        assertFalse(validator.isValid("XX", null));
    }

    @Test
    void shouldNotAcceptJapansAlpha3Code() {
        this.validator = new ValidCountryCodeValidator();
        ValidCountryCodeValidator validator = this.validator;
        assertFalse(validator.isValid("JPN", null));
    }

    @Test
    void shouldShouldAcceptNull() {
        this.validator = new ValidCountryCodeValidator();
        ValidCountryCodeValidator validator = this.validator;
        assertTrue(validator.isValid(null, null));
    }
}
