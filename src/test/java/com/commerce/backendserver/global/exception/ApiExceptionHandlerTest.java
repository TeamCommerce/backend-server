package com.commerce.backendserver.global.exception;

import com.commerce.backendserver.global.exception.ApiExceptionHandlerTest.TestController;
import com.commerce.backendserver.global.exception.error.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TestController.class)
@DisplayName("ApiExceptionHandler Test")
class ApiExceptionHandlerTest {

    private static final String BASE_URL = "/test";

    @Autowired
    private MockMvc mockMvc;

    @RestController
    static class TestController {

        @GetMapping("/test/method-param-ex")
        public String test1(
                @Validated @ModelAttribute RequestDto dto
        ) {
            return "ok";
        }

        @GetMapping("/test/commerce-ex")
        public String test2() {
            throw CommerceException.of(new ErrorCode() {
                public String getMessage() {
                    return "error";
                }

                public HttpStatus getStatus() {
                    return HttpStatus.BAD_REQUEST;
                }
            });
        }
    }

    //Test Resource

    @Getter
    @NoArgsConstructor
    static class RequestDto {

        @NotBlank(message = "Not Blank")
        private String request;
    }

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
