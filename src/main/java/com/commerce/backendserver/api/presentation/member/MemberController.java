package com.commerce.backendserver.api.presentation.member;

import com.commerce.backendserver.domain.member.Member;
import com.commerce.backendserver.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.commerce.backendserver.domain.member.Member.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody String memberName) {
        Member savedMember = memberRepository.save(of(memberName));
        return ResponseEntity
                .ok()
                .body(savedMember);
    }
}
