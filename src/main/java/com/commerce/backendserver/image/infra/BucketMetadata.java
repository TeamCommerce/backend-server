package com.commerce.backendserver.image.infra;

import static com.commerce.backendserver.image.exception.ImageError.*;

import java.util.Arrays;
import java.util.function.Function;

import com.commerce.backendserver.global.exception.CommerceException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BucketMetadata {
	REVIEW(
		"review",
		filename -> String.format("review/%s", filename)
	),
	PRODUCT(
		"product",
		filename -> String.format("product/%s", filename)
	),
	;

	private final String type;
	private final Function<String, String> of;

	public static String generateFilename(String filename, String type) {
		return Arrays.stream(values())
			.filter(metadata -> metadata.getType().equals(type))
			.findAny()
			.orElseThrow(() -> CommerceException.of(INVALID_IMAGE_TYPE))
			.of.apply(filename);
	}
}
