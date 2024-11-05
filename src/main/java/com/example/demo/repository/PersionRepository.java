package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Persion;

@Repository
public interface PersionRepository extends JpaRepository<Persion, Long> {
}
