package com.commerce.backendserver.api.member;

import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    void success2() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.of("h-beeen"));
        //when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(savedMember.getNickName()).isEqualTo(foundMember.getNickName());
    }

    @Test
    void success3() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.of("h-beeen"));
        //when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(savedMember.getNickName()).isEqualTo(foundMember.getNickName());
    }

    @Test
    void success4() throws Exception {
        //given
        Member savedMember = memberRepository.save(Member.of("h-beeen"));
        //when
        Member foundMember = memberRepository.findById(savedMember.getId()).get();
        //then
        assertThat(savedMember.getNickName()).isEqualTo(foundMember.getNickName());
    }
}
