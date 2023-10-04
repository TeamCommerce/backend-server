package com.commerce.backendserver.global.exception;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.commerce.backendserver.common.base.WebMvcTestBase;
import com.commerce.backendserver.common.controller.TestController;

@WebMvcTest(controllers = TestController.class)
@DisplayName("ApiExceptionHandler Test")
class ApiExceptionHandlerTest extends WebMvcTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Exception Handling Test")
    class exceptionHandler {

        private static final String BASE_URL = "/test";

        @Test
        @DisplayName("[MethodArgumentNotValidException]")
        void handleMethodArgumentNotValidException() throws Exception {
            //given
            String emptyString = "";

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(BASE_URL + "/method-param-ex")
				.queryParam("request", emptyString);

			//when
			ResultActions actions = mockMvc.perform(requestBuilder);

			//then
			assertErrorResponse(actions, status().isBadRequest(), "Not Blank");
		}

        @Test
        @DisplayName("[CommerceException]")
        void handleCommerceException() throws Exception {
            //given
            MockHttpServletRequestBuilder badRequest = MockMvcRequestBuilders
                .get(BASE_URL + "/commerce-ex?isServerError=false");

            MockHttpServletRequestBuilder internalServerError = MockMvcRequestBuilders
                .get(BASE_URL + "/commerce-ex?isServerError=true");

			//when
			ResultActions badRequestActions = mockMvc.perform(badRequest);
			ResultActions serverErrorActions = mockMvc.perform(internalServerError);

			//then
			assertErrorResponse(badRequestActions, status().isBadRequest(), "error");
			assertErrorResponse(serverErrorActions, status().is5xxServerError(), "error");
		}
    }

	private static void assertErrorResponse(
		ResultActions actions,
		ResultMatcher statusMather,
		String errorMessage
	) throws Exception {
		actions.andExpectAll(
			statusMather,
			jsonPath("$.errorMessage").value(errorMessage),
			jsonPath("$.errorCode").isNotEmpty()
		);
	}
}
