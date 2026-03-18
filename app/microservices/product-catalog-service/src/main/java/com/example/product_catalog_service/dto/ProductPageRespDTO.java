package com.example.product_catalog_service.dto;

import com.example.product_catalog_service.model.ProductSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductPageRespDTO {
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<ProductSummary> products;
}