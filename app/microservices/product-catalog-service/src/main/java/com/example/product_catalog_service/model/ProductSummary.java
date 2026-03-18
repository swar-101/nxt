package com.example.product_catalog_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductSummary {
    private Long id;
    private String name;
    private String description;
    private CategorySummary category;
    private String imageUrl;
}