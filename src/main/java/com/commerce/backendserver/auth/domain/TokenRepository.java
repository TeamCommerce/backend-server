package com.commerce.backendserver.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {
}
