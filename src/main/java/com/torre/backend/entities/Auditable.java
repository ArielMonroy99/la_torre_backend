package com.torre.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.security.Timestamp;


@Data
@MappedSuperclass
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {

    @CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

    @CreatedBy
    @Column(nullable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = true)
    private String updatedBy;
}
