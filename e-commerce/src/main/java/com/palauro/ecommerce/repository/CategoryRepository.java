package com.palauro.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.Category;


/* 
    Fornece métodos para acessar e manipular dados relacionados à entidade Category.
 */

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
