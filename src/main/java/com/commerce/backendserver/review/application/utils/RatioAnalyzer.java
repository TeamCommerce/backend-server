package com.commerce.backendserver.review.application.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;

@Component
public class RatioAnalyzer {

	public Map<String, Map<String, RatioStatistic>> analyzeAdditionalInfo(
		List<List<AdditionalInfo>> additionalInfoData
	) {
		Map<String, Map<String, Integer>> infoMap = new HashMap<>();
		additionalInfoData.forEach(additionalInfoList ->
			additionalInfoList.forEach(additionalInfo -> {
				String infoName = additionalInfo.getInfoName().getValue();
				infoMap.put(infoName, infoMap.getOrDefault(infoName, new HashMap<>()));

				Map<String, Integer> infoValueMap = infoMap.get(infoName);
				String infoValue = additionalInfo.getInfoValue();

				infoValueMap.put(infoValue, infoValueMap.getOrDefault(infoValue, 0) + 1);
			}));

		Map<String, Map<String, RatioStatistic>> result = new HashMap<>();

		infoMap.keySet()
			.forEach(nameKey -> {
				Map<String, RatioStatistic> ratioMap = new HashMap<>();
				Map<String, Integer> countMap = infoMap.get(nameKey);

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

	public Map<String, RatioStatistic> analyzeScore(List<Integer> scores) {

		Map<Integer, Integer> scoreMap = new HashMap<>();
		scores.forEach(score ->
			scoreMap.put(score, scoreMap.getOrDefault(score, 0) + 1)
		);

		Map<String, RatioStatistic> result = new HashMap<>();
		scoreMap.keySet()
			.forEach(key ->
				result.put(
					String.valueOf(key),
					generateRatioStatistic(scoreMap.get(key), scores.size())
				));

		return result;
	}

	private RatioStatistic generateRatioStatistic(int now, int total) {
		return new RatioStatistic(now, calculateRatio(now, total));
	}

	private double calculateRatio(double now, double total) {
		return (now / total) / 100;
	}
}
