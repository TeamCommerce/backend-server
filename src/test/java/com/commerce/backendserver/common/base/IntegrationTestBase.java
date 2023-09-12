package com.commerce.backendserver.common.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;

import static com.commerce.backendserver.common.utils.TokenUtils.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.commerce.backendserver.auth.infra.jwt.JwtProvider;
import com.commerce.backendserver.common.fixture.MemberFixture;
import com.commerce.backendserver.member.domain.Member;
import com.commerce.backendserver.member.domain.MemberRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public abstract class IntegrationTestBase {

    protected static final String DEFAULT_PATH = "{class_name}/{method_name}/";

    protected RequestSpecification spec;

    @LocalServerPort
    private int port;

    @Autowired
    private JwtProvider provider;

    @BeforeEach
    void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
                .setPort(port)
                .addFilter(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
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
}
