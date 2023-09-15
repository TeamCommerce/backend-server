package com.commerce.backendserver.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberQueryRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthId(String oauthId);
}