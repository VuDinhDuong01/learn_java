package com.example.demo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
// khi chạy ứng dụng là sử lý
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    int min();
    // 3 property basic 
    String message() default "Invalid date of birth";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 
