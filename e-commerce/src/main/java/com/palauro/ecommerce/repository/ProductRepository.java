package com.palauro.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.Product;

/*
    Fornece métodos para acessar e manipular dados relacionados à entidade
    Product. Ele inclui um método para buscar todos os produtos por um
    determinado ID de categoria, usando uma consulta gerada automaticamente pelo
    Spring Data JPA.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_Id(int id);

}
