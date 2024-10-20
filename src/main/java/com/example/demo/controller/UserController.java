package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody @Valid UserRequest entity) {

        // ApiResponse<User> ApiResponse = new ApiResponse<User>();
        ApiResponse<User> apiResponse= ApiResponse.<User>builder().message("create success").result(userService.createUser(entity)).build();
        // ApiResponse.setResult(userService.createUser(entity));
        // ApiResponse.setMessage("create success");
        return  apiResponse;
    } 

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUser() {

        // lấy thông tin user đang được authenticainot trong 1 request sử dụng
       var authention=  SecurityContextHolder.getContext().getAuthentication();
       authention.getAuthorities().forEach(e-> log.info(e.getAuthority()));

        // ApiResponse<List<User>> apiResponse= new ApiResponse<List<User>>();
        ApiResponse<List<User>> apiResponse = ApiResponse.<List<User>>builder().message("success").result(userService.getAllUser()).build();
        // apiResponse.setMessage("success");
        // apiResponse.setResult(userService.getAllUser());
        return apiResponse;
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

    @GetMapping("users/info")
    public ApiResponse<UserResponse> getMyInfo() {
        return   ApiResponse.<UserResponse>builder()
        // .result(userService.getMyInfo())
        .build();
    }
    
}
