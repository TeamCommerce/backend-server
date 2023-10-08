package com.commerce.backendserver.global.auditing;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {

	@Column(
		nullable = false,
		insertable = false,
		updatable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP")
	@CreatedDate
	@JsonFormat(
		shape = STRING,
		pattern = "yyyy-MM-dd a HH:mm")
	private LocalDateTime createdDate;

	@Column(
		nullable = false,
		insertable = false,
		columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	@LastModifiedDate
	@JsonFormat(
		shape = STRING,
		pattern = "yyyy-MM-dd a HH:mm")
	private LocalDateTime updatedDate;
}
