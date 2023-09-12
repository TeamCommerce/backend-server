package com.commerce.backendserver.review.integration;

import static com.commerce.backendserver.common.utils.S3LinkUtils.*;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static io.restassured.RestAssured.given;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.commerce.backendserver.common.base.IntegrationTestBase;
import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.member.domain.MemberRepository;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductCommandRepository;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;

import io.restassured.specification.RequestSpecification;

@DisplayName("[ReviewApi Test] (API)")
class ReviewApiTest extends IntegrationTestBase {

	private static final String REVIEW = "review";
	private static final String BASE_URL = "/api/reviews";

	@MockBean
	private AmazonS3Client amazonS3Client;

	@Autowired
	private ProductCommandRepository productCommandRepository;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() throws IOException {
		BDDMockito.given(amazonS3Client.putObject(any(PutObjectRequest.class)))
			.willReturn(null);

		final URL mockUrl = new URL(createUploadLink(REVIEW));
		BDDMockito.given(amazonS3Client.getUrl(anyString(), anyString()))
			.willReturn(mockUrl);

		productCommandRepository.save(Product.toProduct(
				ProductCommonInfo.of(
					ProductBrand.ADADIS,
					"product",
					ProductCategory.BOT,
					"description"),
				ProductPriceAttribute.of(
					null, 1000
				)
			)
		);

	}

	@Nested
	@DisplayName("[Register Review API]")
	class registerReviewApi {

		@Test
		@DisplayName("success")
		void success() {
			//given
			Map<String, String> params = generateParams();

			RequestSpecification request = given(spec).log().all()
				.filter(documentRequest())
				.header(AUTHORIZATION, generateAccessToken(memberRepository))
				.multiPart("files", getFile())
				.request();

			params.keySet().forEach(key -> request.multiPart(key, params.get(key)));

			//when, then
			request.when()
				.post(BASE_URL)
				.then().log().all()
				.statusCode(CREATED.value());
		}
	}

	private RestDocumentationFilter documentRequest() {
		return document(
			DEFAULT_PATH,
			requestParts(
				partWithName("files").description("리뷰 사진"),
				partWithName("contents")
					.description("리뷰 텍스트")
					.attributes(constraint("공백 제외 5자 이상")),
				partWithName("score")
					.description("리뷰 점수")
					.attributes(constraint("1~5 사이 정수")),
				partWithName("productId")
					.description("리뷰할 상품 ID"),
				partWithName("additionalInfo")
					.description("추가정보 이름/추가정보 값")
					.attributes(constraint("지정된 이름만 사용가능"))
			)
		);
	}

	private static File getFile() {
		final String BASE_PATH = "images/";
		try {
			return new ClassPathResource(BASE_PATH + "hello1.jpg").getFile();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, String> generateParams() {
		Map<String, String> params = new HashMap<>();
		ReviewFixture fixture = ReviewFixture.A;

		params.put("contents", fixture.getContents());
		params.put("score", String.valueOf(fixture.getScore()));
		params.put("productId", String.valueOf(1L));
		fixture.getStringInfoSet().forEach(info -> params.put("additionalInfo", info));

		return params;
	}
}
