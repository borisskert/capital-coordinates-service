package de.borisskert.capitalcoordinatesservice.validation;

import com.neovisionaries.i18n.CountryCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        final CountryCode foundCountryCode = CountryCode.getByCode(value);

        if (foundCountryCode == null) {
            return false;
        }

        return foundCountryCode.getAlpha2().equals(value);
    }
}
