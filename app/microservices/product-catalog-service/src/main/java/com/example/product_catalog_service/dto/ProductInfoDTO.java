package com.example.product_catalog_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductInfoDTO {

    private Long id;
    private String name;
    private String description;

    private Boolean isActive;

    private Double price;
    private Integer stock;

    public boolean isActive() {
        return Boolean.TRUE.equals(this.isActive);
    }
}