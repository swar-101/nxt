package com.example.product_catalog_service.service;

import com.example.product_catalog_service.dto.ProductInfoDTO;
import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.entity.State;
import com.example.product_catalog_service.exception.ProductNotFoundException;
import com.example.product_catalog_service.port.ProductSearchIndexer;
import com.example.product_catalog_service.repo.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class InternalProductService {

    private final ProductRepository productRepository;
    private final ProductSearchIndexer productSearchIndexer;

    public InternalProductService(ProductRepository productRepository,
                                  ProductSearchIndexer productSearchIndexer) {

        this.productRepository = productRepository;
        this.productSearchIndexer = productSearchIndexer;
    }

    public Product create(Product product) {
        Product saved = productRepository.save(product);
        productSearchIndexer.index(saved);
        return saved;
    }

    public Product update(Long productId, Product updated) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());

        Product saved = productRepository.save(existing);
        productSearchIndexer.index(saved);

        return saved;
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
        productSearchIndexer.delete(productId);
    }

    public ProductInfoDTO getProductInfoForCart(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return new ProductInfoDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getState() == State.ACTIVE,
                product.getPrice(),
                product.getStock()
        );
    }
}