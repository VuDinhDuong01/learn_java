package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
// sẽ nhóm tất cả các Autowired thành 1 constructor.
@RequiredArgsConstructor
// makeFinal làm cho các thuộc tính được khai báo đều là final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    // @Autowired
     UserRepository userRepository;

    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }
    // @Autowired
     UserMapper userMapper;

    public User createUser(UserRequest request) {
    // User user = new User();

    // tạo ra đối tượng mà k cần truyền tất cả các tham số trong constructor.
        UserRequest request1 = UserRequest.builder()
        .firstName("")
        .lastName("")
        .password("")
        .username("")
        .build();

        
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // dùng mapper thì sẽ map user.
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // user.setDob(request.getDob());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setPassword(request.getPassword());
        // user.setUsername(request.getUsername());
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
        // user.setDob(entity.getDob());
        // user.setFirstName(entity.getFirstName());
        // user.setLastName(entity.getLastName());
        // user.setUsername(entity.getUsername());
         userMapper.updateUser(user,entity);
        return userRepository.save(user);
    }

    public void deleteUser(String id){
         userRepository.deleteById(id);
    }
}
