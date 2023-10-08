package com.commerce.backendserver.image.domain;

import static jakarta.persistence.GenerationType.*;
import static jakarta.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
@NoArgsConstructor(access = PROTECTED)
@Table(name = "t_image")
public abstract class Image {

	@Id
	@Column(name = "image_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String url;

	protected Image(String url) {
		this.url = url;
	}
}
