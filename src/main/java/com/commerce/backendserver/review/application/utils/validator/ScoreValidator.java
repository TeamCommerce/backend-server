package com.commerce.backendserver.review.application.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.IntStream;

public class ScoreValidator implements ConstraintValidator<ValidScore, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(ValidScore constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return IntStream.rangeClosed(min, max)
                .anyMatch(number -> number == value);
    }
}
