package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest request) {
        User user = new User();
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        user.setDob(request.getDob());
        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
    
        return userRepository.findAll();
    }

    public User getDetailUser(String id){
        return  userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

    public User updateUser(String id , UserUpdate entity){
        User user = getDetailUser(id);
        user.setDob(entity.getDob());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setUsername(entity.getUsername());
        return userRepository.save(user);
    }

    public void deleteUser(String id){
         userRepository.deleteById(id);
    }
}
