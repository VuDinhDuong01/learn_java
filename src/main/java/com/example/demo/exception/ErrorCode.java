package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // khởi tạo giá trị mặc định cho ErrorCode.
    USER_EXISTED(400, "user existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(4230, "username must be 4", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "INVALID MESSAGE KEY", HttpStatus.BAD_REQUEST),
    NOT_NULL(400, "NOT NULL", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(400, "USER_NOT_EXISTED", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1008,"Your age must be at least {min}", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
