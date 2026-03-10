package com.Rychlewski.marketplace.service;

import com.Rychlewski.marketplace.dto.request.CreateProductRequest;
import com.Rychlewski.marketplace.dto.request.UpdateProductRequest;
import com.Rychlewski.marketplace.dto.response.ProductResponse;
import com.Rychlewski.marketplace.entity.Category;
import com.Rychlewski.marketplace.entity.Product;
import com.Rychlewski.marketplace.exception.ResourceNotFoundException;
import com.Rychlewski.marketplace.mapper.ProductMapper;
import com.Rychlewski.marketplace.repository.CategoryRepository;
import com.Rychlewski.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return ProductMapper.toResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findByActiveTrue();
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findByIdAndActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found"));
        Product product = ProductMapper.toEntity(request);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        product.setActive(false);
        productRepository.save(product);
    }

}
