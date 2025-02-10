package com.example.product_catalog_service.controller;

import com.example.product_catalog_service.dto.Ack;
import com.example.product_catalog_service.dto.CategoryDTO;
import com.example.product_catalog_service.dto.ProductDTO;
import com.example.product_catalog_service.entity.Category;
import com.example.product_catalog_service.service.NxtStoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NxtProductController.class)
public class NxtProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NxtStoreService nxtStoreService;

    @Test
    public void testCreateProduct_success() {
        // arrange
        ProductDTO productDTO = createMockProduct();

        Ack ack = new Ack();
        ack.setId(123L);
        ack.setMessage("m1");

        // act
        Mockito.when(nxtStoreService.createProduct(productDTO)).thenReturn(ack);

        // assert
//        mockMvc.perform(post())
    }

    @Test
    public void testGetProductById_success() {
        // arrange
        ProductDTO productDTO = createMockProduct();

    }

    private ProductDTO createMockProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("n1");
        productDTO.setDescription("d1");
        CategoryDTO category = new CategoryDTO();
        category.setName("cn1");
        category.setDescription("dc1");
        productDTO.setCategory(category);
        productDTO.setImageUrl("u1");

        return productDTO;
    }
}