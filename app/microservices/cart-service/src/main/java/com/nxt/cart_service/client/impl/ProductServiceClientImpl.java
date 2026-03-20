package com.nxt.cart_service.client.impl;

import com.nxt.cart_service.client.ProductServiceClient;
import com.nxt.cart_service.client.dto.ProductInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceClientImpl implements ProductServiceClient {

    @Value("${product.service.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ProductServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductInfoDTO getProduct(Long productId) {
        String url = baseUrl + "/internal/products/" + productId;

        try {
            return restTemplate.getForObject(url, ProductInfoDTO.class);
        } catch (Exception e) {
            // TODO: Refine this later
            return null;
        }
    }
}
