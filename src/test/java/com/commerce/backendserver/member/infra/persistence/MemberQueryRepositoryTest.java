package com.commerce.backendserver.member.infra.persistence;

import static com.commerce.backendserver.common.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.commerce.backendserver.common.base.RepositoryTestBase;
import com.commerce.backendserver.member.domain.Member;

@DisplayName("[MemberQueryRepository Test] - Infra layer)")
class MemberQueryRepositoryTest extends RepositoryTestBase {

	@Autowired
	private MemberQueryRepository memberQueryRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Member memberA;

	@BeforeEach
	void setUp() {
		//given
		memberA = memberRepository.save(A.toEntity());
	}

	@Test
	@DisplayName("[findByOauthId]")
	void findByOauthIdTest() {
		//when
		Optional<Member> result = memberQueryRepository.findByOauthId(A.getOauthId());

		//then
		assertAll(
			() -> assertThat(result).isPresent(),
			() -> {
				Member member = result.get();
				assertMemberMatching(member, memberA);
			}
		);
	}

	private void assertMemberMatching(Member actual, Member expected) {
		assertAll(
			() -> assertThat(actual.getOauthId()).isEqualTo(expected.getOauthId()),
			() -> assertThat(actual.getOauthType()).isEqualTo(expected.getOauthType()),
			() -> assertThat(actual.getNickname()).isEqualTo(expected.getNickname())
		);
	}
}
