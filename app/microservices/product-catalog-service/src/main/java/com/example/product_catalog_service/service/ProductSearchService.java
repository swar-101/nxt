package com.example.product_catalog_service.service;

import com.example.product_catalog_service.model.ProductSearchDocument;
import com.example.product_catalog_service.repo.ProductSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    public ProductSearchService(ProductSearchRepository productSearchRepository) {
        this.productSearchRepository = productSearchRepository;
    }

    public List<ProductSearchDocument> search(String keyword) {
        return productSearchRepository.searchByNameOrDescription(keyword);
    }
}