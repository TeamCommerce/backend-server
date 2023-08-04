package com.commerce.backendserver.api.member;

import com.commerce.backendserver.domain.member.Member;
import com.commerce.backendserver.infrastructure.persistence.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void success() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.of("h-beeen"));
        //when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(savedMember.getNickName()).isEqualTo(foundMember.getNickName());
    }
}
