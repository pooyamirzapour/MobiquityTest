package com.mobiquity.mypack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService=new ProductServiceImpl();
    }

    @Test
    void givenProductService_whenAddNewProduct_thenItShouldPersist() {
        productService.add(new Product("Hammer"));
        Product product=productService.findByName("Hammer");
        Assertions.assertNotNull(product);
    }
}