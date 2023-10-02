package com.commerce.backendserver.newauth.presentation.dto;

public record OAuthLoginRequest(
	String code,
	String state
) {
}
