package com.itsue.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity @Data
public class Configs {

    @Id
    @Column(length = 45)
    private String code;
    @Lob
    @Column(length = 100)
    private String value;
}
