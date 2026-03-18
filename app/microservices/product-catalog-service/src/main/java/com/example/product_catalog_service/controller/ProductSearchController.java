package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.model.ProductSearchDocument;
import com.example.product_catalog_service.service.ProductSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/search")
    public List<ProductSearchDocument> search(@RequestParam String q) {
        return productSearchService.search(q);
    }
}