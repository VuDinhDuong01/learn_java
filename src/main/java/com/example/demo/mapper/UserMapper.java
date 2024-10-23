package com.example.demo.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.demo.domain.User;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.UserUpdate;



@Mapper(componentModel = "spring")
public interface  UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserRequest request);
    //  source là cái nguồn lấy để map
    // target là object t sẽ map về.
    // lúc này firstName và lastName trả về trùng kiểu dữ liệu
    // dữ liệu sẽ giống thằng lastName
    // khi có ignore thì sẽ trường dữ liệu được trả về null
    // ví dụ @Mapping(target = "lastName", ignore = true) thì lúc này lastName= null
    
    //  UserResponse toUserResponse(User user);
    // cái này báo là update request vào user
    @Mapping(target = "roles",ignore = true)
    void updateUser(@MappingTarget User user, UserUpdate request);
}
