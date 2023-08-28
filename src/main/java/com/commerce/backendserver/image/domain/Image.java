package com.commerce.backendserver.image.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    private String url;

    @Builder
    private Image(
            final String url
    ) {
        this.url = url;
    }

    /* static factory method */
    public static Image of(
            final String url
    ) {
        return Image.builder()
                .url(url)
                .build();
    }
}
