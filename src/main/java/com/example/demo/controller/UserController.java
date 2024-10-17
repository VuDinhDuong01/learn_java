package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody @Valid UserRequest entity) {

        ApiResponse<User> ApiResponse = new ApiResponse();
        ApiResponse.setResult(userService.createUser(entity));
        ApiResponse.setMessage("create success");
        return  ApiResponse;
    } 

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getDetail(@PathVariable("id") String id) {
        return userService.getDetailUser(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserUpdate entity) {
        
        return userService.updateUser(id, entity);
    }

    @DeleteMapping("/users/{id}")
    public String  deleteUser(@PathVariable("id") String id){
         userService.deleteUser(id);
         return "delete user success";
    }
}