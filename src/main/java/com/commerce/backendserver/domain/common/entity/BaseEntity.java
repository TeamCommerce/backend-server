package com.commerce.backendserver.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime createDate;

    @Column(
            nullable = false,
            insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime updateDate;
}
