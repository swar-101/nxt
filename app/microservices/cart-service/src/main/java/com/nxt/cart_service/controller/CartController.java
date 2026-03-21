package com.nxt.cart_service.controller;

import com.nxt.cart_service.dto.AddItemsReqDTO;
import com.nxt.cart_service.entity.Cart;
import com.nxt.cart_service.service.CartService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public Cart getItem(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public Cart removeItemFromCart(@PathVariable Long userId,
                                   @PathVariable Long productId) {

        return cartService.removeItemFromCart(userId, productId);
    }
}