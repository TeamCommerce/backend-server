package com.commerce.backendserver.member.presentation;

import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.commerce.backendserver.member.domain.Member.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestParam String memberName) {

        Member savedMember = memberRepository.save(of(memberName));
        return ResponseEntity
                .created(URI.create("/api/member" + savedMember.getId()))
                .body(savedMember);
    }
}
