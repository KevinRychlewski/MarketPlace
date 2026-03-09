package com.Rychlewski.marketplace.mapper;

import com.Rychlewski.marketplace.dto.request.CreateProductRequest;
import com.Rychlewski.marketplace.dto.response.ProductResponse;
import com.Rychlewski.marketplace.entity.Product;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }

}
