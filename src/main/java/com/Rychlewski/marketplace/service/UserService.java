package com.Rychlewski.marketplace.service;

import com.Rychlewski.marketplace.dto.request.CreateUserRequest;
import com.Rychlewski.marketplace.dto.request.UpdateUserRequest;
import com.Rychlewski.marketplace.dto.response.UserResponse;
import com.Rychlewski.marketplace.entity.User;
import com.Rychlewski.marketplace.enums.RoleEnum;
import com.Rychlewski.marketplace.mapper.UserMapper;
import com.Rychlewski.marketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActive()) {
            throw new RuntimeException("User is not active");
        }
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findByActiveTrue();
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse createUser(CreateUserRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        User newUser = UserMapper.toEntity(user);
        newUser.setRole(RoleEnum.ROLE_USER);
        userRepository.save(newUser);
        return UserMapper.toResponse(newUser);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActive()) {
            throw new RuntimeException("User is not active");
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            if (!request.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmail(request.getEmail())) {
                    throw new RuntimeException("Email already in use");
                }
                user.setEmail(request.getEmail());
            }
        }
        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isActive()) {
            throw new RuntimeException("User already deactivated");
        }
        user.setActive(false);
        userRepository.save(user);
    }
}
