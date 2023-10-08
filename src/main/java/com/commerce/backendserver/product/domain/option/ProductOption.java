package com.commerce.backendserver.product.domain.option;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "t_product_option")
@NoArgsConstructor(access = PROTECTED)
public class ProductOption extends BaseEntity {

	@Id
	@Column(name = "product_option_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Embedded
	private ProductColor color;

	@Embedded
	private ProductAdditionalOption additionalOption;

	@Column(nullable = false)
	private Integer inventory;

	@Enumerated(value = STRING)
	private ProductStatus status;

	@Enumerated(value = STRING)
	private ProductSize size;

	//== Constructor Method ==//
	@Builder
	private ProductOption(
		ProductColor color,
		ProductAdditionalOption additionalOption,
		Integer inventory,
		ProductStatus status,
		ProductSize size
	) {
		this.color = color;
		this.additionalOption = additionalOption;
		this.inventory = inventory;
		this.status = status;
		this.size = size;
	}

	//== Static Factory Method ==//
	public static ProductOption of(
		final ProductColor color,
		final ProductAdditionalOption additionalOption,
		final Integer inventory,
		final ProductStatus status,
		final ProductSize size
	) {
		return ProductOption.builder()
			.color(color)
			.additionalOption(additionalOption)
			.inventory(inventory)
			.status(status)
			.size(size)
			.build();
	}

	public void updateProduct(Product product) {
		this.product = product;
	}
}
