package com.example.demo.dto.request;

import java.time.LocalDate;

import com.example.demo.validator.DobConstraint;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
// đồng thời tạo ra 2 constructor.
@NoArgsConstructor
@AllArgsConstructor
@Builder
// làm cho các thuộc tính là private không cần phải khai báo nữa
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @NotNull(message = "NOT_NULL")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 20, message = "password must be at least a created")
    @NonNull
    @NotBlank
    String password;

    String firstName;

    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
