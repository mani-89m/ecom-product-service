package com.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dynamodb.model.Product;
import com.productservice.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Product API", description = "CRUD operations for Product")
public class ProductServiceController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/create")
    @ApiOperation(value = "Create a new product", response = Product.class)
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
	Product savedProduct = productService.createProduct(product);
	return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    
    @GetMapping("/products")
    @ApiOperation(value = "Retrieve all products", response = List.class)
    public List<Product> getProducts() {
        return productService.getProducts();
    }
    
    @GetMapping("/product/{id}")
    @ApiOperation(value = "Retrieve a product by ID", response = Product.class)
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }
    
    @PostMapping("/products")
    @ApiOperation(value = "Retrieve requested products", response = List.class)
    public List<Product> getProductsByIds(@RequestBody List<String> productIds) {
	return productService.getProductsByIds(productIds);
    }

}
