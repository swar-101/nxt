package com.nxt.cart_service.client;

import com.nxt.cart_service.client.dto.ProductInfoDTO;

public interface ProductServiceClient {
    ProductInfoDTO getProduct(Long productId);
}
