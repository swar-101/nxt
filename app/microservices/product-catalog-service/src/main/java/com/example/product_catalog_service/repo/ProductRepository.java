package com.example.product_catalog_service.repo;

import com.example.product_catalog_service.entity.Product;
import com.example.product_catalog_service.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p
        JOIN FETCH p.category
        WHERE p.id = :id AND p.state = :state
    """)
    Optional<Product> findProductDetails(@Param("id") Long id, @Param("state") State state);

    Page<Product> findByCategoryIdAndState(Long categoryId, State state, Pageable pageable);
}