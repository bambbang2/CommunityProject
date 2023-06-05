package com.issuemarket.dto;

import lombok.Data;

@Data
public class BoardSearch {

    private int page = 1;

    private int pageSize = 20;

    private String sopt;

    private String skey;
}
