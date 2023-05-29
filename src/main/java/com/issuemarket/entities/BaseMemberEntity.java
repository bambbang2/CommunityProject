package com.issuemarket.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMemberEntity extends BaseEntity {

    @CreatedBy
    @Column(length = 40, updatable = false)
    private String createdBy; // 생성자

    @LastModifiedBy
    @Column(length = 40, insertable = false)
    private String modifiedBy; // 수정자
}
