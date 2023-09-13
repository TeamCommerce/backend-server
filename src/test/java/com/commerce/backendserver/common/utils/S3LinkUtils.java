package com.commerce.backendserver.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class S3LinkUtils {

    private static final String BUCKET_NAME = "bucketName";
    private static final String CLOUD_DOMAIN = "https://cloud-domain";

    public static String createUploadLink(String directory) {
        return String.format(
                "%s/%s/%s/%s",
                CLOUD_DOMAIN,
                BUCKET_NAME,
                directory,
                UUID.randomUUID() + ".jpg"
        );
    }
}
