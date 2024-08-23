package com.productservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamodb.model.Product;
import com.productservice.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getProducts(){
	Iterable<Product> products = productRepository.findAll();
	List<Product> productList = new ArrayList<>();
	products.forEach(productList::add);
	return productList;
    }
    
    public Product createProduct(final Product product) {
	Product p = productRepository.save(product);
	return p;
    }
    
    public Product getProductById(final String productId) {
	return productRepository.findById(productId).orElse(null);
    }
    
    public List<Product> getProductsByIds(final List<String> productIds) {
	Iterable<Product> products = productRepository.findAllById(productIds);
	List<Product> productList = new ArrayList<>();
	products.forEach(productList::add);
	return productList;
    }

}
