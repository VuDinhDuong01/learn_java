package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.demo.domain.Role;
import com.example.demo.dto.request.AggregateRequest;
import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.AggregateRespones;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.dto.response.UploadFileResponse;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    Cloudinary  cloudinary;
    UploadFileService uploadFileService;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var role = roleRepository.findAll();
        return role.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    @SuppressWarnings("unchecked")
    public AggregateRespones<Role> customAggregateRole(AggregateRequest request) {
        int limit = request.getLimit() > 0 ? request.getLimit() : 10;
        int skip = Math.max(request.getStart(), 0);
        Sort sort = customSort(request.getSortField(), request.getSortType());

        Pageable pageable = PageRequest.of(skip, limit, sort);

        Specification<Role> spec = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList();
            for (AggregateRequest.Condition cond : request.getConditions()) {
                var path = root.get(cond.getKey());
                if (Boolean.class.equals(path.getJavaType())) {
                    predicates.add(criteriaBuilder.equal(path, Boolean.valueOf(cond.getValue().get(0))));
                }
                predicates.add(path.in(cond.getValue()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Role> rolePage = roleRepository.findAll(spec, pageable);
        List<Role> allRole = rolePage.getContent();
        AggregateRespones result = new AggregateRespones<>();
        result.setCurrent(skip);
        result.setPageSize(limit);
        result.setTotal((int) rolePage.getTotalElements());
        result.setPages(rolePage.getTotalPages());
        result.setResults(allRole);
        return result;
    }

    private Sort customSort(String sortField, String sortType) {
        if (sortField == null || sortField.isEmpty()) {
            return Sort.by("description").descending();
        }
        return sortType == "asc" ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
    }

    public List<UploadFileResponse> uploadImage(List<MultipartFile> file) {
        return uploadFileService.uploadImage(file);
    }
}
