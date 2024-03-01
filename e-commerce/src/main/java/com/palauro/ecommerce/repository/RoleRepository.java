package com.palauro.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    
}
