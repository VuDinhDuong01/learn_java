package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_EXISTED(1001,"user existed"),
    USERNAME_INVALID(4230,"username must be 4"),
    INVALID_KEY(1002,"INVALID MESSAGE KEY");
  

    ErrorCode(int code, String message){
        this.code= code;
        this.message= message;
    }
    private int code;
    private String message;

}
