package com.example.demo.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class UserUpdate {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}


