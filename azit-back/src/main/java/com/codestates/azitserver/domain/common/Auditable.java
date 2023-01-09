package com.codestates.azitserver.domain.common;

import lombok.Getter;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class Auditable {
    @CreatedDate
    @ColumnDefault("CURRENT_TIMESTAMP()")
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @ColumnDefault("CURRENT_TIMESTAMP()")
    @Column(name = "LAST_MODIFIED_AT", nullable = false)
    private LocalDateTime lastModifiedAt = LocalDateTime.now();
}
