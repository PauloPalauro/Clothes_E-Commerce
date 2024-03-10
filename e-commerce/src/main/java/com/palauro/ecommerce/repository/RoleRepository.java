package com.palauro.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.Role;

/* 
    Fornece métodos para acessar e manipular dados relacionados à entidade Role.
 */


public interface RoleRepository extends JpaRepository<Role, Integer> {

    
}
