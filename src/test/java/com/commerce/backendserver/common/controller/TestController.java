package com.commerce.backendserver.common.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.global.exception.error.ErrorCode;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RestController
public class TestController {

	@GetMapping("/test/method-param-ex")
	public String test1(
		@Validated @ModelAttribute RequestDto dto
	) {
		return "ok";
	}

	@GetMapping("/test/commerce-ex")
	public String test2(@RequestParam boolean isServerError) throws CommerceException {
		if (isServerError) {
			throw generateCommerceEx(INTERNAL_SERVER_ERROR);
		}

		throw generateCommerceEx(BAD_REQUEST);
	}

	private CommerceException generateCommerceEx(HttpStatus httpStatus) {
		return CommerceException.of(new ErrorCode() {
			public String getMessage() {
				return "error";
			}

			public HttpStatus getStatus() {
				return httpStatus;
			}

			public String getCode() {
				return "errorCode";
			}
		});
	}

	//Test Resource
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	static class RequestDto {

		@NotBlank(message = "Not Blank")
		private String request;
	}
}
