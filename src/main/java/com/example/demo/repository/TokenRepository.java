package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Token;


@Repository
public interface TokenRepository extends JpaRepository<Token , String>{

    
}
