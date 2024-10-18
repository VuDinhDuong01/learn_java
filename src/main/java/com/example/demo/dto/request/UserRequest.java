package com.example.demo.dto.request;

import java.time.LocalDate;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotNull(message = "NOT_NULL")
    @Size(min =3 , message="USERNAME_INVALID")
    private String username;

    @Size(min = 8, max = 20, message = "password must be at least a created")
    @NonNull
    @NotBlank
    private String password;

    private String firstName;

    private String lastName;
    
    private LocalDate dob;
}
