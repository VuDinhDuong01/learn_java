package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.controller.RoleController;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
  String id;
  String username;
  String firstName;
  String lastName;
  LocalDate dob;
  Set<RoleController> roles;
}
