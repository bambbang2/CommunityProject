package com.issuemarket.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResult {
    private Long id;
    private LocalDateTime connected_at;
    private Profile properties;
}

