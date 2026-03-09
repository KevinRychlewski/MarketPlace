package com.Rychlewski.marketplace.service;

import com.Rychlewski.marketplace.dto.request.CreateCategoryRequest;
import com.Rychlewski.marketplace.dto.request.UpdateCategoryRequest;
import com.Rychlewski.marketplace.dto.response.CategoryResponse;
import com.Rychlewski.marketplace.entity.Category;
import com.Rychlewski.marketplace.exception.BusinessException;
import com.Rychlewski.marketplace.exception.ResourceNotFoundException;
import com.Rychlewski.marketplace.mapper.CategoryMapper;
import com.Rychlewski.marketplace.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if  (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("Category with the same name already exists");
        }
        Category category = CategoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toResponse(savedCategory);
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        return CategoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findByActiveTrue();
        return categories.stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        if (request.getName() != null) {
            if (!request.getName().equals(category.getName())) {
                if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
                    throw new BusinessException("Category with the same name already exists");
                }
                category.setName(request.getName());
            }
        }
            Category updatedCategory = categoryRepository.save(category);
            return CategoryMapper.toResponse(updatedCategory);
        }


    public void deleteCategory(Long id) {
        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        category.setActive(false);
        categoryRepository.save(category);
    }
}
