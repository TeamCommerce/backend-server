package com.commerce.backendserver.member.domain;

import static com.commerce.backendserver.common.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Member Test] - Domain layer")
class MemberTest {

	@Test
	@DisplayName("[Construct method]")
	void createMemberTest() {
		//given, when
		Member member = A.toEntity();

		//then
		assertAll(
			() -> assertThat(member.getOauthId()).isEqualTo(A.getOauthId()),
			() -> assertThat(member.getOauthType()).isEqualTo(A.getOauthType()),
			() -> assertThat(member.getNickname()).isEqualTo(A.getNickname())
		);
	}

	@Test
	@DisplayName("[updateFromOAuth method]")
	void updateFromOAuthTest() {
		//given
		Member member = A.toEntity();

		//when
		final String newNickname = "new nickname";
		member.updateFromOAuth(newNickname);

		//then
		assertThat(member.getNickname()).isEqualTo(newNickname);
	}
}
