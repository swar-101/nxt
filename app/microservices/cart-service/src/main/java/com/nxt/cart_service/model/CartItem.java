package com.nxt.cart_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Long productId;
    private String name;
    private Double priceAtAdd;
    private int quantity;
    private Boolean quantityCapped;
    private Double totalPrice; // quantity * priceAtAdd
}