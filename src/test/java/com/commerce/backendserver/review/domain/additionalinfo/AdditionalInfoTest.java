package com.commerce.backendserver.review.domain.additionalinfo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

@DisplayName("[AdditionalInfo Test] (Domain layer)")
public class AdditionalInfoTest {

	@Test
	@DisplayName("[of method]")
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
}
