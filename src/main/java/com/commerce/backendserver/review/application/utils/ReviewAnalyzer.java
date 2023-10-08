package com.commerce.backendserver.review.application.utils;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReviewAnalyzer {

    private static final String SIZE = InfoName.SIZE.getValue();

    private final RatioAnalyzer ratioAnalyzer;

    public ReviewStatistics analyzeReview(List<Review> reviews) {
        List<Integer> scoreData = toScoreData(reviews);
        List<List<AdditionalInfo>> additionalInfoData = toAdditionalInfoData(reviews);

        Map<String, RatioStatistic> scoreStatistic = ratioAnalyzer.analyzeScore(scoreData);

        Map<String, Map<String, RatioStatistic>> additionalInfoStatistic =
                ratioAnalyzer.analyzeAdditionalInfo(additionalInfoData);

        int totalReviewCount = reviews.size();

        return new ReviewStatistics(
                totalReviewCount,
                calculateAverage(totalReviewCount, calculateTotalScore(scoreData)),
                additionalInfoStatistic.get(SIZE).keySet(),
                scoreStatistic,
                additionalInfoStatistic
        );
    }

    private int calculateTotalScore(List<Integer> scoreData) {
        return scoreData.stream().mapToInt(i -> i).sum();
    }

    private List<List<AdditionalInfo>> toAdditionalInfoData(List<Review> reviews) {
        return reviews.stream()
                .map(Review::getAdditionalInfoList)
                .toList();
    }

    private List<Integer> toScoreData(List<Review> reviews) {
        return reviews.stream()
                .map(Review::getScore)
                .toList();
    }

    private double calculateAverage(double size, double sum) {
        return sum / size;
    }
}
