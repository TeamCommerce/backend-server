package com.commerce.backendserver.product.domain.option;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductAdditionalOption {

	@Column(
		name = "additional_option_key",
		columnDefinition = "varchar(30)")
	private String key;

	@Column(
		name = "additional_option_value",
		columnDefinition = "varchar(30)")
	private String value;

	private Integer additionalFee;

	//== Constructor Method ==//
	@Builder
	private ProductAdditionalOption(
		final String key,
		final String value,
		final Integer additionalFee
	) {
		this.key = key;
		this.value = value;
		this.additionalFee = additionalFee;
	}

	//== Static Factory Method ==//
	public static ProductAdditionalOption of(
		final String key,
		final String value,
		final Integer additionalFee
	) {
		return ProductAdditionalOption.builder()
			.key(key)
			.value(value)
			.additionalFee(additionalFee)
			.build();
	}
}
