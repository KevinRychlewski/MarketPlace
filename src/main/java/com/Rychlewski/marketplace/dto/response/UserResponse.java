package com.Rychlewski.marketplace.dto.response;

import com.Rychlewski.marketplace.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private RoleEnum role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
