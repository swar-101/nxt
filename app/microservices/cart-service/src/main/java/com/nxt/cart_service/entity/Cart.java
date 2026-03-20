package com.nxt.cart_service.entity;

import com.nxt.cart_service.model.CartItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private State state;

    private Long userId;
    private List<CartItem> cartItems;

    private Double totalPrice;

    private Instant createdAt;
    private Instant updatedAt;
}