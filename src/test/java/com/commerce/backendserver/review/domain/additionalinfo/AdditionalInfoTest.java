package com.commerce.backendserver.review.domain.additionalinfo;

import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.common.fixture.ReviewFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[AdditionalInfo Test] - Domain layer")
public class AdditionalInfoTest {

	@Test
	@DisplayName("[of]")
	void ofTest() {
		//given
		final InfoName infoName = InfoName.SIZE;
		final String infoValue = "Large";

		//when
		AdditionalInfo result = AdditionalInfo.of(infoName, infoValue);

		//then
		assertAll(
			() -> assertThat(result.getInfoName()).isEqualTo(infoName),
			() -> assertThat(result.getInfoValue()).isEqualTo(infoValue)
		);
	}

	@Test
	@DisplayName("[registerReview method]")
	void registerReview() {
		//given
		final Review review = A.toEntity(null, null);
		final AdditionalInfo additionalInfo = AdditionalInfo.of(InfoName.SIZE, "Large");

		//when
		additionalInfo.registerReview(review);

		//then
		assertThat(additionalInfo.getReview()).isEqualTo(review);
	}
}
