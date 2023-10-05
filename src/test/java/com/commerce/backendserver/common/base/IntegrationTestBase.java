package com.commerce.backendserver.common.base;

import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.jdbc.Sql;

import com.commerce.backendserver.auth.infra.jwt.JwtTokenProvider;
import com.commerce.backendserver.common.fixture.MemberFixture;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.infra.persistence.MemberRepository;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
@Sql("/sql/clean.sql")
public abstract class IntegrationTestBase {

	protected static final String DEFAULT_PATH = "{class_name}/{method_name}/";

	protected RequestSpecification spec;

	@LocalServerPort
	private int port;

	@Autowired
	private JwtTokenProvider provider;

	@BeforeEach
	void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
		this.spec = new RequestSpecBuilder()
			.setPort(port)
			.addFilter(documentationConfiguration(restDocumentation)
				.operationPreprocessors()
				.withRequestDefaults(prettyPrint())
				.withResponseDefaults(prettyPrint()))
			.build();

		this.spec.config(
			RestAssuredConfig.config()
				.redirect(RedirectConfig.redirectConfig()
					.followRedirects(false)))
		;
	}

	protected Attributes.Attribute constraint(String value) {
		return new Attributes.Attribute("constraints", value);
	}

	protected String generateAccessToken(MemberRepository memberRepository) {
		final Long memberId = saveMember(memberRepository);
		return BEARER.concat(provider.createAccessToken(memberId));
	}

	private Long saveMember(MemberRepository memberRepository) {
		final Member member = MemberFixture.A.toEntity();
		return memberRepository.save(member).getId();
	}

	protected void assertErrorResponse(
		ValidatableResponse response,
		HttpStatus status,
		String errorMessage
	) {
		response.statusCode(status.value())
			.body("timeStamp", notNullValue(String.class))
			.body("errorCode", notNullValue())
			.body("errorMessage", is(errorMessage));
	}

	protected RestDocumentationFilter documentErrorResponse() {
		return document(
			DEFAULT_PATH,
			responseFields(
				fieldWithPath("timeStamp").description("에러 발생 시간"),
				fieldWithPath("errorCode").description("에러 상태코드"),
				fieldWithPath("errorMessage").description("에러 메세지")
			)
		);
	}
}
