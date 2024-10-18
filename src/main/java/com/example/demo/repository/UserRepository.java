package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    
    // tự động java kiểm tra 
    boolean existsByUsername(String username);
    
} 
