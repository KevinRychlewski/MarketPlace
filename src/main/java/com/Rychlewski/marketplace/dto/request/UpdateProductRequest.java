package com.Rychlewski.marketplace.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {

    @Size(max = 150, message = "O nome do produto deve conter no máximo 150 caracteres")
    private String name;

    @Size(max = 500, message = "A descrição do produto deve conter no máximo 500 caracteres")
    private String description;

    @Positive(message = "O preço do produto deve ser um valor maior que zero")
    private BigDecimal price;

    @PositiveOrZero(message = "O estoque do produto deve ser um valor maior ou igual a zero")
    private Integer stock;

}
