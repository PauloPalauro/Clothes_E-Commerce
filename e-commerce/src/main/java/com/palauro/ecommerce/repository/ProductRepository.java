package com.palauro.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
