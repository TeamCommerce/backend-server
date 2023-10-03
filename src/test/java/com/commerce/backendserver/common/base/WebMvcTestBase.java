package com.commerce.backendserver.common.base;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.commerce.backendserver.newauth.domain.AuthTokenManager;

public abstract class WebMvcTestBase {

	@MockBean
	private AuthTokenManager authTokenManager;

	@BeforeEach
	void setUpLogin() {
		doNothing().when(authTokenManager).validateToken(any());
		given(authTokenManager.getId(any())).willReturn(1L);
	}
}
