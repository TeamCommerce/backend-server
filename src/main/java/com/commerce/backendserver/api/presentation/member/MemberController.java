package com.commerce.backendserver.api.presentation.member;

import com.commerce.backendserver.domain.member.Member;
import com.commerce.backendserver.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.commerce.backendserver.domain.member.Member.of;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestParam String memberName) {

        Member savedMember = memberRepository.save(of(memberName));

        return ResponseEntity
                .ok()
                .body(savedMember);
    }
}
