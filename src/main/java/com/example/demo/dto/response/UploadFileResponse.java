package com.example.demo.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
@Builder
public class UploadFileResponse {
   String  resource_type ;
    String secure_url;
}
