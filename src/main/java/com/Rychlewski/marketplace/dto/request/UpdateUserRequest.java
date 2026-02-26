package com.Rychlewski.marketplace.dto.request;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @Size(max = 100, message = "O nome deve conter no máximo 100 caracteres")
    @Nullable
    private String name;

    @Email
    private String email;

}
