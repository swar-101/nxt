package com.nxt.cart_service.client.dto;

import lombok.Getter;

@Getter
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