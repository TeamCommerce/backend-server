package com.commerce.backendserver.member.domain;

import com.commerce.backendserver.auth.infra.oauth.OAuthType;
import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "t_member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String oauthId;

    private String oauthType;

    private String nickname;

    //생성 메소드
    private Member(
            String nickName,
            String oauthId,
            String oauthType
    ) {
        this.nickname = nickName;
        this.oauthId = oauthId;
        this.oauthType = oauthType;
    }

    public static Member createMember(
            String nickName,
            String oauthId,
            String oauthType
    ) {
        return new Member(nickName, oauthId, oauthType);
    }

    //편의 메서드
    public void updateFromOAuth(String nickname) {
        this.nickname = nickname;
    }
}
