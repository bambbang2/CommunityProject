package com.issuemarket.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
    private String nickname;
    private String profile_image;
    private String thumbnail_image;
}
