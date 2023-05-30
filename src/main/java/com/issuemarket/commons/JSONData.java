package com.issuemarket.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class JSONData<T> {

    private boolean success;

    private HttpStatus status = HttpStatus.OK;

    private String message;

    private T data;
}
