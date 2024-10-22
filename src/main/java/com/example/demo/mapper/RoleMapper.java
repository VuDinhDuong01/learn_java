package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.domain.Role;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;

@Mapper(componentModel  ="spring")
public interface RoleMapper {

    @Mapping(target = "Permissions", ignore = true)
    Role toRole(RoleRequest request);
    
    RoleResponse toRoleResponse(Role role);
} 
