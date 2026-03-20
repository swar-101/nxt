package com.nxt.cart_service.controller;

import com.nxt.cart_service.dto.AddItemsReqDTO;
import com.nxt.cart_service.entity.Cart;
import com.nxt.cart_service.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public Cart addItem(@RequestBody AddItemsReqDTO req) {
        return cartService.addItem(
                req.getUserId(),
                req.getProductId(),
                req.getQuantity()
        );
    }
}