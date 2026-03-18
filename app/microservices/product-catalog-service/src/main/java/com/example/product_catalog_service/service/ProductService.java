package com.example.product_catalog_service.service;

import com.example.product_catalog_service.dto.ProductDetailsRespDTO;
import com.example.product_catalog_service.dto.ProductPageRespDTO;
import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.entity.State;
import com.example.product_catalog_service.exception.ProductNotFoundException;
import com.example.product_catalog_service.model.CategorySummary;
import com.example.product_catalog_service.model.ProductSummary;
import com.example.product_catalog_service.repo.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {};
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ObjectMapper objectMapper) {

        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public ProductDetailsRespDTO getProductDetails(Long productId) {
        Product product = productRepository
                .findProductDetails(productId, State.ACTIVE)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Map<String, Object> specifications = parseJson(product.getSpecifications());
        Map<String, Object> metadata = parseJson(product.getMetadata());

        CategorySummary category =
                new CategorySummary(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                );

        return new ProductDetailsRespDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                category,
                product.getImageUrls(),
                specifications,
                metadata);
    }

    private Map<String, Object> parseJson(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(json, MAP_TYPE);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid JSON in product data", e);
        }
    }

    public ProductPageRespDTO getProductsByCategory(Long categoryId, int page, int size) {
        Page<Product> productPage = productRepository
                .findByCategoryIdAndState(
                        categoryId,
                        State.ACTIVE,
                        PageRequest.of(page, size)
                );

        List<ProductSummary> products = productPage
                .getContent()
                .stream()
                .map(product -> {
                    CategorySummary categorySummary = new CategorySummary(
                            product.getCategory().getId(),
                            product.getCategory().getName()
                    );

                    String imageUrl = product.getImageUrls().isEmpty()
                            ? null
                            : product.getImageUrls().get(0);
                    
                    return new ProductSummary(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            categorySummary,
                            imageUrl
                    );
                })
                .toList();

        return new ProductPageRespDTO(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalPages(),
                productPage.getTotalElements(),
                products
        );
    }
}