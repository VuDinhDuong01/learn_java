package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// những cái nào trả về  null thì k cần show ra;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int code = 201;
    private String error;
    private String message;
    private  T result;
}
