package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.dto.ProductPageRespDTO;
import com.example.product_catalog_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{categoryId}/products")
    public ProductPageRespDTO getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size

    ) {
        return productService.getProductsByCategory(categoryId, page, size);
    }
}