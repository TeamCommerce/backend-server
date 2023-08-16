package com.commerce.backendserver.member.domain;

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

    private String nickname;

    //생성 메소드
    private Member(String nickName, String oauthId) {
        this.nickname = nickName;
        this.oauthId = oauthId;
    }

    public static Member createMember(
            String nickName,
            String oauthId
    ) {
        return new Member(nickName, oauthId);
    }
}
