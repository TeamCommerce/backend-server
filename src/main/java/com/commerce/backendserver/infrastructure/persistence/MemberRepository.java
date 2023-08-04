package com.commerce.backendserver.infrastructure.persistence;

import com.commerce.backendserver.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
}
