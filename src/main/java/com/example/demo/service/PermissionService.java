package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Permission;
import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level =AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    final PermissionRepository permissionRepository;
    final PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest request) {

        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public  List<PermissionResponse> getAll(){
        var permissions =  permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionName){
        permissionRepository.deleteById(permissionName);
    }
}
