package com.example.product_catalog_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseModel {

    private String name;

    @Column(length = 5000)
    private String description;

    @ManyToOne
    private Category category;

    @ElementCollection
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(columnDefinition = "json")
    private String specifications;

    @Column(columnDefinition = "json")
    private String metadata;

    private Double price;

    private Integer stock;
}