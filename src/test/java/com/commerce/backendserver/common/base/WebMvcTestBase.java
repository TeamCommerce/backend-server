package com.commerce.backendserver.common.base;

import com.commerce.backendserver.auth.domain.AuthTokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;

public abstract class WebMvcTestBase {

    @MockBean
    private AuthTokenManager authTokenManager;

    @BeforeEach
    void setUpLogin() {
        doNothing().when(authTokenManager).validateToken(any());
        given(authTokenManager.getId(any())).willReturn(1L);
    }
}
