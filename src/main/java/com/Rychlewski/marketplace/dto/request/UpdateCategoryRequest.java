package com.Rychlewski.marketplace.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    @Size(max = 100, message = "O nome da categoria deve conter no máximo 100 caracteres")
    private String name;

}
