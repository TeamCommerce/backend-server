package com.commerce.backendserver.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    void deleteByMemberId(Long memberId);

    Optional<Token> findByMemberId(Long memberId);
}
