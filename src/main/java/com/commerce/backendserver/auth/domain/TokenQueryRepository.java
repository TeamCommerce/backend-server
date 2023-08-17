package com.commerce.backendserver.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TokenQueryRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMemberId(Long memberId);
}
