package com.example.demo.config;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthentioncationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        ErrorCode  errorCode= ErrorCode.USER_EXISTED;
        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse= ApiResponse.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .build();

        // conver Object to string 
        ObjectMapper objectMapper= new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        // trả về request cho client với 401
        response.flushBuffer();
    }
    
}
