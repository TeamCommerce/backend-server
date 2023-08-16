package com.commerce.backendserver.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    void deleteByMemberId(Long memberId);
}
