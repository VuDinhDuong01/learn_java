package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleController {
    final RoleService roleService;

    @GetMapping("/role")
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
        .result(roleService.getAll())
        .build();
    }

    @PostMapping("/role")
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest entity) {
       
        
        return ApiResponse.<RoleResponse>builder()
        .result(roleService.create(entity))
        .build();
    }

    @DeleteMapping("/role/{name}")
    public ApiResponse<Void> deleteRole(@PathVariable String name){
        return ApiResponse.<Void>builder()
        // .result(roleService.delete(name))
        .build();
    }
    
    

}
