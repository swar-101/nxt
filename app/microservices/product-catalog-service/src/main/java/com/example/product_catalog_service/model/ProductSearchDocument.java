package com.example.product_catalog_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@AllArgsConstructor
@Document(indexName = "products")
public class ProductSearchDocument {

    @Id
    private String id;

    private String name;
    private String description;
    private String category;
    private Double price;
}