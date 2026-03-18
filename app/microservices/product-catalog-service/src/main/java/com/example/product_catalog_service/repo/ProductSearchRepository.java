package com.example.product_catalog_service.repo;

import com.example.product_catalog_service.model.ProductSearchDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, String> {

    @Query("""
            {
                "multi_match" : {
                    "query": "?0",
                    "fields": ["name", "description"],
                    "fuzziness": "AUTO"
                }
            }
    """)
    List<ProductSearchDocument> searchByNameOrDescription(String keyword);
}