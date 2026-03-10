package com.Rychlewski.marketplace.mapper;

import com.Rychlewski.marketplace.dto.request.CreateCategoryRequest;
import com.Rychlewski.marketplace.dto.response.CategoryResponse;
import com.Rychlewski.marketplace.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {
        CategoryResponse request = new CategoryResponse();
        request.setId(category.getId());
        request.setName(category.getName());
        request.setCreatedAt(category.getCreatedAt());
        request.setUpdatedAt(category.getUpdatedAt());
        return request;
    }

}
