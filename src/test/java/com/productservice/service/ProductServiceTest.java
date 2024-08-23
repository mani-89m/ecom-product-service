package com.productservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dynamodb.model.Product;
import com.productservice.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testGetProducts() {
	List<Product> productList = new ArrayList<>();
	Product p = new Product();
	p.setProductId("UUID");
	p.setName("Test");
	p.setDescription("Test Desc");
	productList.add(p);
	when(productRepository.findAll()).thenReturn(productList);
	List<Product> outputProducts = productService.getProducts();
	assertNotNull(outputProducts);
	assertEquals(outputProducts.size(), 1);
	Product p1 = outputProducts.get(0);
	assertEquals(p1.getProductId(), p.getProductId());
	verify(productRepository, times(1)).findAll();
    }

}
