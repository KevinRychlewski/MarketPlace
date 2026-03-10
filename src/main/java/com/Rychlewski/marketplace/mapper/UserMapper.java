package com.Rychlewski.marketplace.mapper;

import com.Rychlewski.marketplace.dto.request.CreateUserRequest;
import com.Rychlewski.marketplace.dto.response.UserResponse;
import com.Rychlewski.marketplace.entity.User;

public class UserMapper {

    public static User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

}
