package com.issuemarket.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Configs {

    @Id
    @Column(length = 45)
    private String code;
    @Lob
    @Column(length = 100)
    private String value;
}
