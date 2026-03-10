package com.Rychlewski.marketplace.controller;

import com.Rychlewski.marketplace.dto.request.CreateCategoryRequest;
import com.Rychlewski.marketplace.dto.request.UpdateCategoryRequest;
import com.Rychlewski.marketplace.dto.response.CategoryResponse;
import com.Rychlewski.marketplace.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> setCategoryActiveStatus(@PathVariable Long id) {
        CategoryResponse response = categoryService.setCategoryActiveStatus(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


}
