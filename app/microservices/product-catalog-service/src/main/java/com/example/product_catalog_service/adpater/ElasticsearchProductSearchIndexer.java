package com.example.product_catalog_service.adpater;

import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.mapper.ProductSearchMapper;
import com.example.product_catalog_service.port.ProductSearchIndexer;
import com.example.product_catalog_service.repo.ProductSearchRepository;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchProductSearchIndexer implements ProductSearchIndexer {

    private final ProductSearchRepository productSearchRepository;

    public ElasticsearchProductSearchIndexer(ProductSearchRepository productSearchRepository) {
        this.productSearchRepository = productSearchRepository;
    }

    @Override
    public void index(Product product) {
        try {
            productSearchRepository.save(ProductSearchMapper.toDocument(product));
        } catch (Exception e) {
            System.err.println("Failed to index product " + product.getId());
        }
    }

    @Override
    public void delete(Long productId) {
        try {
            productSearchRepository.deleteById(String.valueOf(productId));
        } catch (Exception e) {
            System.err.println("Failed to index product " + productId);
        }
    }
}