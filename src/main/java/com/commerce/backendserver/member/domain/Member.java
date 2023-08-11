package com.commerce.backendserver.member.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "user_tb")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickName;

    public static Member of(
            String nickName
    ) {
        return Member.builder()
                .nickName(nickName)
                .build();
    }
}
