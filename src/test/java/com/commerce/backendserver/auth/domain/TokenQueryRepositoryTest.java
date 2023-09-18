package com.commerce.backendserver.auth.domain;

import com.commerce.backendserver.common.base.RepositoryTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.commerce.backendserver.common.utils.TokenUtils.TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[TokenQueryRepository Test] - Domain layer")
class TokenQueryRepositoryTest extends RepositoryTestBase {

    @Autowired
    private TokenQueryRepository tokenQueryRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private Token token;
    private final Long memberId = 1L;

    @BeforeEach
    void setUp() {
        //given
        token = tokenRepository.save(Token.issue(TOKEN, memberId));
    }

    @Nested
    @DisplayName("[findByMemberId]")
    class findByMemberIdTest {

        @Test
        @DisplayName("[success]")
        void success() {
            //when
            Optional<Token> result = tokenQueryRepository.findByMemberId(memberId);

            //then
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> {
                        Token actual = result.get();
                        assertThat(actual.getMemberId()).isEqualTo(memberId);
                        assertThat(actual.getRefreshToken()).isEqualTo(token.getRefreshToken());
                    }
            );
        }
    }
}
