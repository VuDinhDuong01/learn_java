package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.domain.Permission;
import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request );
    PermissionResponse toPermissionResponse(Permission permission);
}
