package com.example.demo.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;
import com.example.demo.enums.Role;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
// sẽ nhóm tất cả các Autowired thành 1 constructor.
@RequiredArgsConstructor
// makeFinal làm cho các thuộc tính được khai báo đều là final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    // @Autowired
     UserRepository userRepository;

    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }
    // @Autowired
     UserMapper userMapper;

     PasswordEncoder  passwordEncoder;
     RoleRepository roleRepository;

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

            // khi tạo user cùng 1 lúc thì throw này k check.
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // dùng mapper thì sẽ map user.
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles= new HashSet<String>();
        roles.add(Role.USER.name());

       try{
        user= userRepository.save(user);
       }catch(DataIntegrityViolationException exception){
        // show error
        
       }
        // user.setRoles(roles);
        // user.setDob(request.getDob());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setPassword(request.getPassword());
        // user.setUsername(request.getUsername());
        return userRepository.save(user);
    }


    // tạo 1 proxy khi nào là admin.
    // kiểm tra trc khi gọi method
    // hasRole sẽ work khi có ROLE đứng đầu 
    // k có thì dùng hasAuthority
    @PreAuthorize("hasRole('ADMIN')")  
    public List<User> getAllUser(){
        log.info("in method get user");
        return userRepository.findAll();
    }
     

    // kiểm tra sau khi gọi method
    // check nếu user đang đang nhập === với username lúc authentication.
    @PostAuthorize("returnObject.username== authentication.name") 
    public User getDetailUser(String id){
        // vẫn được gọi nhưng k trả về
        // PostAuthorize thằng làm cho gọi xong thì mới check roles nên k trả về  dữ liệu khi khác roles.
       log.warn("In method get user by id");
        return  userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
    }

    public User updateUser(String id , UserUpdate entity){
         User user = getDetailUser(id);
        // user.setDob(entity.getDob());
        // user.setFirstName(entity.getFirstName());
        // user.setLastName(entity.getLastName());
        // user.setUsername(entity.getUsername());
         userMapper.updateUser(user,entity);
         user.setPassword(passwordEncoder.encode(entity.getFirstName()));
         var roles =  roleRepository.findAllById(entity.getRoles());
         user.setRoles(new HashSet<>(roles));
        return userRepository.save(user);
    }

    public void deleteUser(String id){
         userRepository.deleteById(id);
    }


    public  User getMyInfo(){
       var context =  SecurityContextHolder .getContext();
       String name =context.getAuthentication().getName();
      User user = userRepository.findByUsername(name).orElseThrow(()-> new AppException(ErrorCode.USER_EXISTED));
    //   return userMapper.toUserResponse(user);
    return user;
    }
}
