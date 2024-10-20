package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.service.PermissionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PermissionController {
    PermissionService permissionService;


    @GetMapping("/permission")
    public ApiResponse<List<PermissionResponse>> getListPermission() {
        return ApiResponse.<List<PermissionResponse>>builder()
        .result(permissionService.getAll())
        .
        build();
    }

    @PostMapping("/permission")
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
        .result(permissionService.create(request))
        .build();
    }

    @DeleteMapping("/permission/{id}")
    public ApiResponse<Void> deletePermission(@PathVariable String id){
        return ApiResponse.<Void>builder()
        // .result(permissionService.delete(id))
        .build();
    }
}
