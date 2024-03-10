package com.palauro.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.palauro.ecommerce.model.User;

/* 
    Fornece métodos para acessar e manipular dados relacionados à entidade User.
    Ele inclui um método para buscar um usuário pelo seu email, usando uma
    consulta gerada automaticamente pelo Spring Data JPA
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

}
