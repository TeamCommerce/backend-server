package com.commerce.backendserver.review.application.utils.validator;


import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.review.application.dto.request.AdditionalInfoRequest;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import static com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName.*;
import static com.commerce.backendserver.review.exception.ReviewError.INVALID_INTEGER_INFO_VALUE;

public class AdditionalInfoValidator implements
        ConstraintValidator<ValidAdditionalInfo, Set<AdditionalInfoRequest>>
{

    @Override
    public boolean isValid(Set<AdditionalInfoRequest> value, ConstraintValidatorContext context) {
        value.forEach(target -> {
            InfoName infoName = matchInfoName(target.infoName());

            if (infoName.getType().isInstance(Integer.class)) {
                validateIsInteger(infoName.getValue());
            }
        });

        return true;
    }

    private void validateIsInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw CommerceException.of(INVALID_INTEGER_INFO_VALUE);
        }
    }
}
