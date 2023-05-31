package com.issuemarket.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuForm {

    private String code;
    private String name;
    private String url;
}
