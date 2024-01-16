package de.borisskert.capitalcoordinatesservice.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidCountryCodeValidatorTest {

    private ValidCountryCodeValidator validator;

    @BeforeEach
    void setup() {
        this.validator = new ValidCountryCodeValidator();
    }

    @Test
    void shouldAcceptCodeForGermany() {
        assertThat(validator.isValid("DE", null)).isTrue();
    }

    @Test
    void shouldShouldNotAcceptCodeForXX() {
        assertThat(validator.isValid("XX", null)).isFalse();
    }

    @Test
    void shouldNotAcceptJapansAlpha3Code() {
        assertThat(validator.isValid("JPN", null)).isFalse();
    }

    @Test
    void shouldShouldAcceptNull() {
        assertThat(validator.isValid(null, null)).isTrue();
    }
}
