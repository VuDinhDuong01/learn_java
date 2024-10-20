package com.example.demo.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service

@RequiredArgsConstructor
@Slf4j

@FieldDefaults(level  =  AccessLevel.PRIVATE)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermission(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.tRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var role = roleRepository.findAll();
        return role.stream().map(roleMapper::tRoleResponse).toList();
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
