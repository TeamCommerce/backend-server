package com.commerce.backendserver.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.commerce.backendserver.common.fixture.MemberFixture.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Member Test] (Domain layer)")
public class MemberTest {

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
