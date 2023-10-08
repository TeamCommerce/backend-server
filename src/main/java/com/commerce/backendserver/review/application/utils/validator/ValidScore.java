package com.commerce.backendserver.review.application.utils.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ScoreValidator.class)
public @interface ValidScore {

	int min() default 1;

	int max() default 5;

	String message() default "1에서 5사이의 정수 별점을 입력해주세요.";

	Class[] groups() default {};

	Class[] payload() default {};
}
