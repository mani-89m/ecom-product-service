package com.productservice.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.dynamodb.model.Product;

@EnableScan
public interface ProductRepository extends CrudRepository<Product, String> {

}
