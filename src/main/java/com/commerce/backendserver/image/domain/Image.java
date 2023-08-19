package com.commerce.backendserver.image.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.image.exception.ImageError.INVALID_IMAGE_URL;
import static org.springframework.util.StringUtils.hasText;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    private String url;

    private String path;

    @Builder
    private Image(
            final String url,
            final String path
    ) {
        validateUrl(url);
        validatePath(path);
        this.url = url;
        this.path = path;
    }

    /* static factory method */
    public static Image of(
            final String url,
            final String path
    ) {
        return Image.builder()
                .url(url)
                .path(path)
                .build();
    }

    /* validation */
    public void validateUrl(final String url) {
        if (!hasText(url)) {
            throw CommerceException.of(INVALID_IMAGE_URL);
        }
    }

    public void validatePath(final String path) {
        if (!hasText(path)) {
            throw CommerceException.of(INVALID_IMAGE_URL);
        }
    }
}
