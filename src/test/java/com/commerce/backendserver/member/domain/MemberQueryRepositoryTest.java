package com.commerce.backendserver.member.domain;

import com.commerce.backendserver.common.base.RepositoryTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.commerce.backendserver.common.fixture.MemberFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[MemberQueryRepository Test] (Domain layer)")
public class MemberQueryRepositoryTest extends RepositoryTestBase {

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
    @DisplayName("[findByOauthId query]")
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
