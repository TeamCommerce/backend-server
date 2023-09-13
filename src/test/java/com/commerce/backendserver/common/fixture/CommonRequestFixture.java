package com.commerce.backendserver.common.fixture;

import static lombok.AccessLevel.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public abstract class CommonRequestFixture {

	public static ValidatableResponse multipartRequest(
		final RequestSpecification spec,
		final Set<RestDocumentationFilter> documentations,
		final String accessToken,
		final List<File> files,
		final Map<String, String> params,
		final String path
	) {
		return request(
			spec,
			documentations,
			given -> {
				RequestSpecification request = given
					.header(AUTHORIZATION, accessToken)
					.request();

				files.forEach(file -> request.multiPart("files", file));
				params.keySet().forEach(key -> request.multiPart(key, params.get(key)));

				return request.when()
					.post(path);
			}
		);
	}

	private static ValidatableResponse request(
		final RequestSpecification given,
		final Set<RestDocumentationFilter> documentations,
		final Function<RequestSpecification, Response> function
	) {
		documentations.forEach(given::filter);
		final Response response = function.apply(given);
		return response.then().log().all();
	}
}
