package com.commerce.backendserver.review.application.dto.request;

import com.commerce.backendserver.review.application.utils.validator.ValidAdditionalInfo;
import com.commerce.backendserver.review.application.utils.validator.ValidContentsLength;
import com.commerce.backendserver.review.application.utils.validator.ValidScore;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public record CreateReviewRequest(
        @ValidScore
        Integer score,

        @ValidContentsLength
        String contents,

        Long productId,

        @ValidAdditionalInfo
        Set<String> additionalInfo,

        List<MultipartFile> files
) {
}
