package com.commerce.backendserver.common.controller;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.global.exception.error.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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

    //Test Resource
    @Getter
    @NoArgsConstructor
    static class RequestDto {

        @NotBlank(message = "Not Blank")
        private String request;
    }
}
