package com.commerce.backendserver.review.application.utils.validator;


import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import static com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName.matchInfoName;
import static com.commerce.backendserver.review.exception.ReviewError.INVALID_ADDITIONAL_INFO;
import static com.commerce.backendserver.review.exception.ReviewError.INVALID_INTEGER_INFO_VALUE;

public class AdditionalInfoValidator implements
        ConstraintValidator<ValidAdditionalInfo, Set<String>> {

    @Override
    public boolean isValid(Set<String> value, ConstraintValidatorContext context) {
        if (value != null) {
            validateAdditionalInfo(value);
        }
        return true;
    }

    private void validateAdditionalInfo(Set<String> value) {
        value.forEach(target -> {
            String[] splitInfo = target.split("/");

            validateInfoFormat(splitInfo);

            InfoName infoName = matchInfoName(splitInfo[0]);
            String infoValue = splitInfo[1];

            if (infoName.getType().isAssignableFrom(Integer.class)) {
                validateIsInteger(infoValue);
            }
        });
    }

    private void validateInfoFormat(String[] splitInfo) {
        if (splitInfo.length != 2) {
            throw CommerceException.of(INVALID_ADDITIONAL_INFO);
        }
    }

    private void validateIsInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw CommerceException.of(INVALID_INTEGER_INFO_VALUE);
        }
    }
}
