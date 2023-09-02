package com.commerce.backendserver.review.application.utils.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdditionalInfoValidator.class)
public @interface ValidAdditionalInfo {

    String message() default "";

    Class[] groups() default {};

    Class[] payload() default {};
}
