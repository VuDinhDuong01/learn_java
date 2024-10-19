package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;
import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, String> {

    
    // tự động java kiểm tra 
    boolean existsByUsername(String username);

    Optional<User>  findByUsername(String username);

    // void save(org.springframework.security.core.userdetails.User user);
    
} 
