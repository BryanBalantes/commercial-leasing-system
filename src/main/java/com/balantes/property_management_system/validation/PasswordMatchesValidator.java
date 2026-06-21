package com.balantes.property_management_system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, ConfirmPasswordInterface> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        // Optional: Initialize validator with annotation properties
    }

    public boolean isValid(ConfirmPasswordInterface obj, ConstraintValidatorContext context) {
        return obj.getPassword() != null && obj.getPassword().equals(obj.getConfirmPassword());
    }
}
