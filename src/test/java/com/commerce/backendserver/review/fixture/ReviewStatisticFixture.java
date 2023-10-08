package com.commerce.backendserver.review.fixture;

import com.commerce.backendserver.review.utils.ReviewAsserter.RatioTestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 해당 Fixture 은 ReviewFixture 의 데이터를 기반으로 제작됨
 * ReviewFixture 가 변경되면 해당 클래스의 데이터도 변경되야함
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReviewStatisticFixture {

    public static final List<String> ADDITIONAL_INFO_KEY_DATA =
            List.of("키", "몸무게", "사이즈");

    public static final List<String> EXIST_SIZE_DATA =
            List.of("Small", "Large", "Medium");

    public static final Set<RatioTestDto> HEIGHT_DATA =
            Set.of(new RatioTestDto("156", 1), new RatioTestDto("172", 2));

    public static final Set<RatioTestDto> WEIGHT_DATA =
            Set.of(new RatioTestDto("60", 1), new RatioTestDto("40", 1));

    public static final Set<RatioTestDto> SIZE_DATA =
            Set.of(
                    new RatioTestDto("Small", 1),
                    new RatioTestDto("Medium", 1),
                    new RatioTestDto("Large", 1)
            );

    public static final Set<RatioTestDto> SCORE_DATA =
            Set.of(
                    new RatioTestDto("2", 1),
                    new RatioTestDto("3", 1),
                    new RatioTestDto("4", 1)
            );
}
