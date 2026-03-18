package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.service.InternalProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/products")
public class InternalProductController {

    private final InternalProductService service;

    public InternalProductController(InternalProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}