package com.example.product_catalog_service.dto;

import com.example.product_catalog_service.model.CategorySummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ProductDetailsRespDTO {
    // wrapper for semantic safety
    private Long id;
    private String name;
    private String description;
    // wrapper for semantic safety
    private CategorySummary category;
    private List<String> images;
    private Map<String, Object> specifications;
    private Map<String, Object> metadata;
}
