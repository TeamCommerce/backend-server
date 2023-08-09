package com.commerce.backendserver.global.exception;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.commerce.backendserver.global.exception.ApiExceptionHandlerTest.TestController;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(value = TestController.class)
@DisplayName("ApiExceptionHandler Test")
public class ApiExceptionHandlerTest {

  private static final String BASE_URL = "/test";

  @Autowired
  private MockMvc mockMvc;

  @Nested
  @DisplayName("Exception Handling Test")
  class exceptionHandler {

    @Test
    @WithMockUser
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
  }

  @RestController
  static class TestController {

    @GetMapping("/test/method-param-ex")
    public String requestTest(
        @Validated @ModelAttribute RequestDto dto
    ) {
      return "ok";
    }
  }

  @Getter
  @NoArgsConstructor
  static class RequestDto {

    @NotBlank(message = "Not Blank")
    private String request;
  }
}
