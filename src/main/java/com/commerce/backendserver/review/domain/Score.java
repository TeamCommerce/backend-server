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
public class Score {

	private static final int MIN = 1;
	private static final int MAX = 5;

	@Column(name = "score", nullable = false)
	private int value;

	public Score(final int score) {
		validateScoreRange(score);
		this.value = score;
	}

	private void validateScoreRange(final int score) {
		if (score < MIN || score > MAX) {
			throw CommerceException.of(INVALID_RANGE_SCORE);
		}
	}
}
