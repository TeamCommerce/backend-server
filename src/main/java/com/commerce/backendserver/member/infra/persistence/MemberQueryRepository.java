package com.commerce.backendserver.member.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.member.domain.Member;

@Transactional(readOnly = true)
public interface MemberQueryRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByOauthId(String oauthId);
}
