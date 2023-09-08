package com.commerce.backendserver.review.application.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContentsLengthValidator implements ConstraintValidator<ValidContentsLength, String> {

    private int min;

    @Override
    public void initialize(ValidContentsLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String trimValue = value.replaceAll(" ", "");

        return trimValue.length() >= min;
    }
}
