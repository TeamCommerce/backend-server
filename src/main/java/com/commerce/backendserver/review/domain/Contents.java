package com.commerce.backendserver.review.domain;

import static com.commerce.backendserver.review.exception.ReviewError.*;
import static lombok.AccessLevel.*;

import com.commerce.backendserver.global.exception.CommerceException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Contents {

	private static final int MIN = 5;

	@Column(name = "contents", nullable = false)
	private String value;

	public Contents(final String contents) {
		validateContentsLength(contents);
		this.value = contents;
	}

	private static void validateContentsLength(final String contents) {
		String trimContents = contents.replaceAll(" ", "");
		if (trimContents.length() < MIN) {
			throw CommerceException.of(INVALID_LENGTH);
		}
	}
}
