package com.commerce.backendserver.auth.application.filter;

import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.common.controller.TestController;
import com.commerce.backendserver.global.config.SecurityConfig;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.global.exception.error.ErrorCode;
import com.commerce.backendserver.member.infra.persistence.MemberQueryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.commerce.backendserver.auth.exception.AuthError.*;
import static com.commerce.backendserver.common.fixture.MemberFixture.A;
import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                SecurityConfig.class,
                                ObjectMapper.class,
                                AuthenticationEntryPoint.class
                        })
        })
@DisplayName("[JwtFilter Test] (Application layer)")
public class JwtFilterTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    @MockBean
    protected SimpleUrlAuthenticationSuccessHandler successHandler;
    @MockBean
    private MemberQueryRepository memberQueryRepository;
    @MockBean
    private JwtProvider jwtProvider;

    @Nested
    @DisplayName("[JwtAuthentication]")
    class JwtAuthenticationFilterTest {

        private static final long PAYLOAD = 1L;
        private static final String TEST_URL = "/api/test";

        @Test
        @DisplayName("success")
        void success() throws Exception {
            //given
            given(memberQueryRepository.findById(PAYLOAD))
                    .willReturn(Optional.of(A.toEntity()));
            doNothing().when(jwtProvider).validateToken(TOKEN);
            given(jwtProvider.getPayload(TOKEN)).willReturn(PAYLOAD);

            //when
            MockHttpServletRequestBuilder requestBuilder = generateAuthenticationRequestBuilder();

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").value(A.getNickname())
                    );
        }

        @Test
        @DisplayName("fail by invalid payload")
        void failByInvalidPayload() throws Exception {
            //given
            given(memberQueryRepository.findById(PAYLOAD))
                    .willReturn(Optional.empty());
            doNothing().when(jwtProvider).validateToken(TOKEN);
            given(jwtProvider.getPayload(TOKEN)).willReturn(PAYLOAD);

            MockHttpServletRequestBuilder requestBuilder = generateAuthenticationRequestBuilder();

            //when
            ResultActions actions = mockMvc.perform(requestBuilder);

            //then
            assertErrorResponse(actions, status().isBadRequest(), TOKEN_INVALID);
        }

        @Test
        @DisplayName("fail by expired token")
        void failByExpiredToken() throws Exception {
            //given
            doThrow(CommerceException.of(TOKEN_EXPIRED))
                    .when(jwtProvider).validateToken(TOKEN);

            MockHttpServletRequestBuilder requestBuilder = generateAuthenticationRequestBuilder();

            //when
            ResultActions actions = mockMvc.perform(requestBuilder);

            //then
            assertErrorResponse(actions, status().isBadRequest(), TOKEN_EXPIRED);
        }

        @Test
        @DisplayName("fail by invalid token")
        void failByInvalidToken() throws Exception {
            //given
            doThrow(CommerceException.of(TOKEN_INVALID))
                    .when(jwtProvider).validateToken(TOKEN);

            MockHttpServletRequestBuilder requestBuilder = generateAuthenticationRequestBuilder();

            //when
            ResultActions actions = mockMvc.perform(requestBuilder);

            //then
            assertErrorResponse(actions, status().isBadRequest(), TOKEN_INVALID);
        }

        @Test
        @DisplayName("fail by no authentication")
        void failByNoAuthentication() throws Exception {
            //given
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TEST_URL);

            //when
            ResultActions actions = mockMvc.perform(requestBuilder);

            //then
            assertErrorResponse(actions, status().isUnauthorized(), NEED_LOGIN);
        }

        @Test
        @DisplayName("fail by not exist bearer")
        void failByNotExistBearer() throws Exception {
            //given
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TEST_URL)
                .header(AUTHORIZATION, TOKEN);

            //when
            ResultActions actions = mockMvc.perform(requestBuilder);

            //then
            assertErrorResponse(actions, status().isUnauthorized(), NEED_LOGIN);
        }

        private MockHttpServletRequestBuilder generateAuthenticationRequestBuilder() {
            return MockMvcRequestBuilders
                    .get(TEST_URL)
                    .header(AUTHORIZATION, BEARER + TOKEN);
        }
    }

    private static void assertErrorResponse(
        ResultActions actions,
        ResultMatcher statusMatcher,
        ErrorCode errorCode
    ) throws Exception {
        actions.andExpectAll(
            statusMatcher,
            jsonPath("$.errorCode").value(errorCode.getStatus().value()),
            jsonPath("$.errorMessage").value(errorCode.getMessage())
        );
    }
}
