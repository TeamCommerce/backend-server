package com.commerce.backendserver.auth.infra;

import com.commerce.backendserver.auth.infra.oauth.UserPrincipal;
import com.commerce.backendserver.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.commerce.backendserver.common.fixture.MemberFixture.A;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[UserPrincipal Test] (Infra layer)")
public class UserPrincipalTest {

    private UserPrincipal principal;
    private Member member;
    private Map<String, Object> attributes;

    @BeforeEach
    void setUp() {
        //given
        member = A.toEntity();
        attributes = new HashMap<>();
        principal = UserPrincipal.of(member, attributes);
    }

    @Test
    @DisplayName("[getAttributes method]")
    void getAttributesTest() {
        //when
        Map<String, Object> result = principal.getAttributes();

        //then
        assertThat(result).isEqualTo(attributes);
    }

    @Test
    @DisplayName("getAuthorities method")
    void getAuthoritiesTest() {
        //when
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        //then
        boolean hasRoleUser = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
        assertThat(hasRoleUser).isTrue();
    }

    @Test
    @DisplayName("getName method")
    void getNameTest() {
        //when
        String name = principal.getName();

        //then
        assertThat(name).isEqualTo(member.getOauthId());
    }

    @Test
    @DisplayName("getId method")
    void getIdTest() {
        //given
        ReflectionTestUtils.setField(member, "id", 1L);

        //when
        Long id = principal.getId();

        //then
        assertThat(id).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("getNickname method")
    void getNicknameTest() {
        //when
        String nickname = principal.getNickname();

        //then
        assertThat(nickname).isEqualTo(member.getNickname());
    }
}
