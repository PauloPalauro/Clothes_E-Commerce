package com.palauro.ecommerce.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.palauro.ecommerce.model.CustomUserDetails;
import com.palauro.ecommerce.model.User;
import com.palauro.ecommerce.repository.UserRepository;

/*
    Este serviço é responsável por carregar os detalhes do usuário com base no email fornecido. 
    Ele usa um UserRepository para buscar os detalhes do usuário no banco de dados e
     retorna um objeto CustomUserDetails contendo esses detalhes.
*/

@Service
public class CustomUserDetailService implements UserDetailsService { //Eta classe implementa a interface UserDetailsService. A interface UserDetailsService é parte do Spring Security e é usada para carregar detalhes do usuário durante a autenticação.

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //Método é parte da interface UserDetailsService e é usado para carregar os detalhes do usuário com base no nome de usuário (nesse caso, o endereço de email). Quando um usuário tenta fazer login, este método é chamado para buscar os detalhes do usuário no banco de dados com base no email fornecido.
        Optional<User> user = userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User error")); //Este trecho de código lança uma exceção UsernameNotFoundException se nenhum usuário for encontrado com o email fornecido. A mensagem de exceção inclui o email fornecido.
        return user.map(CustomUserDetails:: new).get(); // mapeia o usuário encontrado para um objeto CustomUserDetails e o retorna. CustomUserDetails é uma classe que implementa a interface UserDetails e é usada pelo Spring Security para representar os detalhes do usuário durante a autenticação.
    }
}
