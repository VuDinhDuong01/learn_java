package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
// @Getter
// @Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdate {
  String username;
  String firstName;
  String lastName;
  LocalDate dob;
  List<String> roles;
}


