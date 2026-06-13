package com.nxt.cart_service.service;

import com.nxt.cart_service.client.ProductServiceClient;
import com.nxt.cart_service.client.dto.ProductInfoDTO;
import com.nxt.cart_service.entity.Cart;
import com.nxt.cart_service.entity.State;
import com.nxt.cart_service.exception.InvalidQuantityException;
import com.nxt.cart_service.exception.ProductNotFoundException;
import com.nxt.cart_service.exception.ProductUnavailableException;
import com.nxt.cart_service.model.CartItem;
import com.nxt.cart_service.repo.CartRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static final int MAX_QUANTITY = 10;

    private final ProductServiceClient productServiceClient;
    private final CartRepository cartRepository;

    public CartService(ProductServiceClient productServiceClient, CartRepository cartRepository) {
        this.productServiceClient = productServiceClient;
        this.cartRepository = cartRepository;
    }

    // one ACTIVE cart per user
    // quantity >= 1
    // no duplicate product IDs (merge)
    // product must be valid at add time

    public Cart addItem(Long userId, Long productId, int quantity) {
        // userId present
        // productId present
        // quantity >= 1 must not exceed 10
        // -- fail fast
        validateInput(userId, productId, quantity);


        // fetch product
        // exists?
            // active?
            // purchasable?
        ProductInfoDTO product = fetchAndValidateProduct(productId);

        // fetch cart
        // if state != ACTIVE -> reject
            // try redis
            // fallback mongo
        Cart cart = getOrCreateActiveCart(userId);

        // get items
        // exists? increase quantity
            // else add new item
        // enforce max qty 10
        // no duplicate product IDs
        mergeOrAddItem(cart, product, quantity);
        recalculateTotal(cart);
        persist(cart);

        return cart;
    }

    private void validateInput(Long userId, Long productId, int quantity) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (quantity < 1 || quantity > MAX_QUANTITY) {
            throw new InvalidQuantityException("Quantity must be between 1 and " + MAX_QUANTITY);
        }
    }

    private ProductInfoDTO fetchAndValidateProduct(Long productId) {
        ProductInfoDTO productInfoDTO = productServiceClient.getProduct(productId);

        if (productInfoDTO == null) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }

        if (productInfoDTO.getPrice() == null) {
            throw new ProductUnavailableException(
                    "Product " + productId + " has no valid price"
            );
        }

        if (!productInfoDTO.isActive() ||
            productInfoDTO.getStock() == null ||
            productInfoDTO.getStock() <= 0) {

            throw new ProductUnavailableException("Product " + productId + " is inactive or out of stock");
        }

        return productInfoDTO;
    }

    private Cart getOrCreateActiveCart(Long userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserIdAndState(userId, State.ACTIVE);

        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }

        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setState(State.ACTIVE);
        newCart.setCartItems(new ArrayList<>());
        newCart.setTotalPrice(0.0);
        newCart.setCreatedAt(Instant.now());
        newCart.setUpdatedAt(Instant.now());

        return cartRepository.save(newCart);
    }

    private void mergeOrAddItem(Cart cart, ProductInfoDTO product, int quantity) {

        List<CartItem> items = cart.getCartItems();

        if (items == null) {
            items = new ArrayList<>();
            cart.setCartItems(items);
        }

        for (CartItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                int newQuantity = item.getQuantity() + quantity;

                boolean isCapped = newQuantity > MAX_QUANTITY;

                int finalQuantity = Math.min(newQuantity, MAX_QUANTITY);

                item.setQuantity(finalQuantity);
                item.setTotalPrice(item.getPriceAtAdd() * finalQuantity);

                if (isCapped) {
                    item.setQuantityCapped(true);
                }
                // TODO: return info to client if quantity was capped at MAX_QUANTITY

                return;
            }
        }

        int finalQuantity = Math.min(quantity, MAX_QUANTITY);
        CartItem newItem = new CartItem();
        newItem.setProductId(product.getId());
        newItem.setName(product.getName());
        newItem.setPriceAtAdd(product.getPrice());
        newItem.setQuantity(finalQuantity);
        newItem.setTotalPrice(product.getPrice() * finalQuantity);

        items.add(newItem);
    }

    private void recalculateTotal(Cart cart) {
        double cartTotal = 0.0;

        for (CartItem item : cart.getCartItems()) {
            double itemTotal = item.getPriceAtAdd() * item.getQuantity();
            item.setTotalPrice(itemTotal);
            cartTotal += itemTotal;
        }

        cart.setTotalPrice(cartTotal);
        cart.setUpdatedAt(Instant.now());
    }

    private void persist(Cart cart) {
        cartRepository.save(cart);
        // TODO: Save to Redis after introducing dependencies (write-through)
    }
}