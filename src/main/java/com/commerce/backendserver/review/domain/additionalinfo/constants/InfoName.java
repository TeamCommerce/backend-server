package com.commerce.backendserver.review.domain.additionalinfo.constants;

import static com.commerce.backendserver.review.exception.ReviewError.*;

import java.util.function.Consumer;

import com.commerce.backendserver.global.exception.CommerceException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InfoName {

	SIZE("사이즈", 1, infoValue -> {
	}),
	HEIGHT("키", 4, integerTypeValidator()),
	WEIGHT("몸무게", 5, integerTypeValidator());

	private final String value;
	private final int order;
	private final Consumer<String> typeValidator;

	public void validateInfoNameType(final String infoName) {
		typeValidator.accept(infoName);
	}

	private static Consumer<String> integerTypeValidator() {
		return infoValue -> {
			try {
				Integer.parseInt(infoValue);
			} catch (NumberFormatException e) {
				throw CommerceException.of(INVALID_INTEGER_INFO_VALUE);
			}
		};
	}

	public static InfoName getInfoName(String infoName) {
		try {
			return InfoName.valueOf(infoName.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw CommerceException.of(NOT_EXIST_INFO_NAME);
		}
	}
}
