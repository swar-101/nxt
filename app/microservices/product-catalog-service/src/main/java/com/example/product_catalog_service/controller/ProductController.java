package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.dto.ProductDetailsRespDTO;
import com.example.product_catalog_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsRespDTO> getProductDetails(@PathVariable Long id) {
        ProductDetailsRespDTO resp = productService.getProductDetails(id);
        return ResponseEntity.ok(resp);
    }
}