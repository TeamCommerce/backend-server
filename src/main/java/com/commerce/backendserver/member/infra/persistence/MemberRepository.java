package com.commerce.backendserver.member.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.member.domain.Member;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
}
