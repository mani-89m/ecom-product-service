package com.productservice.integration.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import com.dynamodb.model.Product;
import com.productservice.service.ProductService;

@SpringBootTest
@Import(LocalStackConfiguration.class)
@ActiveProfiles("test")
public class ProductServiceIntegrationTest {
    
    @Autowired
    private ProductService productService;
    
    @Test
    public void testCreateProduct() {
	Product p = new Product();
	p.setName("Test");
	p.setProductId("UUID");
	p.setDescription("Test Desc");
	Product savedProduct = productService.createProduct(p);
	assertNotNull(savedProduct);
	assertEquals(p.getName(), savedProduct.getName());
    }
    
}
