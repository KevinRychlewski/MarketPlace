package com.Rychlewski.marketplace.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 150, message = "O nome do produto deve conter no máximo 150 caracteres")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 500, message = "A descrição do produto deve conter no máximo 500 caracteres")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço do produto deve ser um valor maior que zero")
    private BigDecimal price;

    @NotNull(message = "O estoque do produto é obrigatório")
    @PositiveOrZero(message = "O estoque do produto deve ser um valor maior ou igual a zero")
    private Integer stock;

    @NotNull(message = "A categoria do produto é obrigatória")
    private Long categoryId;

}
