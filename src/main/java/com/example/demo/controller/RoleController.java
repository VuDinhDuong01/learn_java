package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Role;
import com.example.demo.dto.request.AggregateRequest;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.AggregateRespones;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/role")
    public ApiResponse<AggregateRespones<Role>> findAll(@RequestBody AggregateRequest request) {
        return ApiResponse.<AggregateRespones<Role>>builder()
                .result(roleService.customAggregateRole(request))
                .build();
    }

    // @PostMapping("/role")
    // public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest entity)
    // {

    // return ApiResponse.<RoleResponse>builder()
    // .result(roleService.create(entity))
    // .build();
    // }

    @DeleteMapping("/role/{name}")
    public ApiResponse<Void> deleteRole(@PathVariable String name) {
        return ApiResponse.<Void>builder()
                // .result(roleService.delete(name))
                .build();
    }

}
