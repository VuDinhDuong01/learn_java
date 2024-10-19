package com.example.demo.dto.request;

import org.springframework.http.HttpStatus;

import com.example.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

// @Getter
// @Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
// những cái nào trả về  null thì k cần show ra;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse<T> {
    
    int code =HttpStatus.CREATED.value();
     String error;
     String message;
      T result;
}
