package com.commerce.backendserver.review.application.utils.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContentsLengthValidator.class)
public @interface ValidContentsLength {

    int min() default 5;

    String message() default "최소 5자 이상 입력해주세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
