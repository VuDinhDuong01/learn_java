package com.example.demo.dto.response;

import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public  class PersonResponse {
        Object name;
        Object address;
        List<String> listError;
        
}
