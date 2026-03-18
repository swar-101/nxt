package com.example.product_catalog_service.mapper;

import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.model.ProductSearchDocument;

public class ProductSearchMapper {

    public static ProductSearchDocument toDocument(Product product) {

        String id = String.valueOf(product.getId());

        String name = product.getName();

        String description = product.getDescription() != null
                ? product.getDescription()
                : "";

        String category = (product.getCategory() != null
                && product.getCategory().getName() != null)
                ? product.getCategory().getName()
                : "";

        Double price = product.getPrice();

        return new ProductSearchDocument(
                id,
                name,
                description,
                category,
                price
        );
    }
}