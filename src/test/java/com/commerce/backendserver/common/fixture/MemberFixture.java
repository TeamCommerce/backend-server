package com.commerce.backendserver.common.fixture;

import com.commerce.backendserver.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberFixture {
    A("google1", "google", "member1"),
    B("google2", "google", "member2"),
    C("kakao1", "kakao", "member3"),
    D("kakao2", "kakao", "member4"),
    ;

    private final String oauthId;
    private final String oauthType;
    private final String nickname;

    public Member toEntity() {
        return Member.createMember(
                nickname, oauthId, oauthType
        );
    }
}
