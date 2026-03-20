package com.nxt.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddItemsReqDTO {
    private Long userId;
    private Long productId;
    private int quantity;
}