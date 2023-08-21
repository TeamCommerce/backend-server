package com.commerce.backendserver.global.exception;

import com.commerce.backendserver.common.controller.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TestController.class)
@DisplayName("ApiExceptionHandler Test")
class ApiExceptionHandlerTest {

    private static final String BASE_URL = "/test";

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Exception Handling Test")
    @WithMockUser
    class exceptionHandler {

        @Test
        @DisplayName("[MethodArgumentNotValidException]")
        void handleMethodArgumentNotValidException() throws Exception {
            //given
            String emptyString = "";

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/method-param-ex")
                    .queryParam("request", emptyString);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.errorMessage").value("Not Blank")
                    );
        }

        @Test
        @DisplayName("[CommerceException]")
        void handleCommerceException() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/commerce-ex");

            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.errorMessage").value("error")
                    );
        }
    }
}
