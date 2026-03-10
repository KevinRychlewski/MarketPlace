package com.Rychlewski.marketplace.service;

import com.Rychlewski.marketplace.dto.request.CreateUserRequest;
import com.Rychlewski.marketplace.dto.request.UpdateUserRequest;
import com.Rychlewski.marketplace.dto.response.UserResponse;
import com.Rychlewski.marketplace.entity.User;
import com.Rychlewski.marketplace.enums.RoleEnum;
import com.Rychlewski.marketplace.exception.BusinessException;
import com.Rychlewski.marketplace.exception.ResourceNotFoundException;
import com.Rychlewski.marketplace.mapper.UserMapper;
import com.Rychlewski.marketplace.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        if (!user.isActive()) {
            throw new BusinessException("User is not active");
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
            throw new BusinessException("Email already in use");
        }
        User newUser = UserMapper.toEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(RoleEnum.ROLE_USER);
        User savedUser = userRepository.save(newUser);
        return UserMapper.toResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        if (!user.isActive()) {
            throw new BusinessException("User is not active");
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            if (!request.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmail(request.getEmail())) {
                    throw new BusinessException("Email already in use");
                }
                user.setEmail(request.getEmail());
            }
        }
        User updatedUser = userRepository.save(user);
        return UserMapper.toResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        if (!user.isActive()) {
            throw new BusinessException("User already deactivated");
        }
        user.setActive(false);
        userRepository.save(user);
    }
}
