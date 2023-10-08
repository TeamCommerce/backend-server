package com.commerce.backendserver.review.application.utils;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RatioAnalyzer {

    /**
     * 모든 리뷰의 AdditionalInfo 를 통해 infoName 를 key 로 Map 을 리턴
     * 각 Map 의 key 는 infoValue 이며 value 는 각 infoValue 별 비율(한 infoName 을 가지는 각각의 infoValue 의 비율)
     */
    public Map<String, Map<String, RatioStatistic>> analyzeAdditionalInfo(
            List<List<AdditionalInfo>> additionalInfoData
    ) {
        Map<String, Map<String, Integer>> countingMap = new HashMap<>();

        //모든 AdditionalInfo 를 탐색하며 비율을 구하기 위한 각 infoName 의 각각의 infoValue 개수를 카운팅
        additionalInfoData.forEach(additionalInfoList ->
                additionalInfoList.forEach(additionalInfo -> {
                    String infoName = additionalInfo.getInfoName().getValue();
                    countingMap.put(infoName, countingMap.getOrDefault(infoName, new HashMap<>()));

                    Map<String, Integer> infoValueMap = countingMap.get(infoName);
                    String infoValue = additionalInfo.getInfoValue();

                    infoValueMap.put(infoValue, infoValueMap.getOrDefault(infoValue, 0) + 1);
                }));

        Map<String, Map<String, RatioStatistic>> result = new HashMap<>();

        //카운팅 된 Map 을 통해서 비율로 변환
        countingMap.keySet()
                .forEach(nameKey -> {
                    Map<String, RatioStatistic> ratioMap = new HashMap<>();
                    Map<String, Integer> countMap = countingMap.get(nameKey);

                    int total = countMap.keySet().stream()
                            .mapToInt(countMap::get)
                            .sum();

                    countMap.keySet()
                            .forEach(valueKey -> {
                                int now = countMap.get(valueKey);
                                ratioMap.put(valueKey, generateRatioStatistic(now, total));
                            });

                    result.put(nameKey, ratioMap);
                });

        return result;
    }

    /**
     * 모든 리뷰의 score 읉 통해 score 마다의 비율을 가진 Map 을 리턴
     */
    public Map<String, RatioStatistic> analyzeScore(List<Integer> scores) {

        Map<Integer, Integer> countingMap = new HashMap<>();
        scores.forEach(score ->
                countingMap.put(score, countingMap.getOrDefault(score, 0) + 1)
        );

        Map<String, RatioStatistic> result = new HashMap<>();
        countingMap.keySet()
                .forEach(key ->
                        result.put(
                                String.valueOf(key),
                                generateRatioStatistic(countingMap.get(key), scores.size())
                        ));

        return result;
    }

    private RatioStatistic generateRatioStatistic(int now, int total) {
        return new RatioStatistic(now, calculateRatio(now, total));
    }

    private double calculateRatio(double now, double total) {
        return (now / total) * 100;
    }
}
