package com.example.demo.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Page<Role> findAll(Specification<Role> spec, Pageable pageable);
} 