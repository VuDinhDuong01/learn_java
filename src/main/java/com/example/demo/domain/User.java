package com.example.demo.domain;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id ;
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;


    // public String getId(){
    //     return id;
    // }

    // public void setId(String id){
    //     this.id = id;
    // }
}