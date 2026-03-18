package com.example.product_catalog_service.port;

import com.example.product_catalog_service.entity.Product;

/**
 * Port for indexing products into search infrastructure.
 * This abstraction allows replacing Elasticsearch with other
 * systems (e.g., Kafka-based indexing, external search services)
 * without impacting domain logic.
 */
public interface ProductSearchIndexer {
    void index(Product product);
    void delete(Long productId);
}