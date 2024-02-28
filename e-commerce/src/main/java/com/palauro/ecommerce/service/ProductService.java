package com.palauro.ecommerce.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.palauro.ecommerce.model.Product;
import com.palauro.ecommerce.repository.ProductRepository;


@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


}