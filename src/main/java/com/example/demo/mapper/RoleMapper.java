package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.domain.Role;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;

@Mapper(componentModel  ="spring")
public interface RoleMapper {

    // bỏ qua permisson, k map vào.
    Role toRole(RoleRequest request);
    RoleResponse tRoleResponse(Role role);
} 
