package com.palauro.ecommerce.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.palauro.ecommerce.model.Role;
import com.palauro.ecommerce.model.User;
import com.palauro.ecommerce.repository.RoleRepository;
import com.palauro.ecommerce.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Este arquivo define um manipulador de sucesso de autenticação para a autenticação OAuth2 do Google. 

@Component // Marca a classe como um componente gerenciado pelo Spring
public class GoogleOauth2SuccessHandler implements AuthenticationSuccessHandler { //Implementa a interface AuthenticationSuccessHandler do Spring Security. Isso significa que ela deve fornecer uma implementação para o método onAuthenticationSuccess, que é chamado quando a autenticação é bem-sucedida.

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // Esta linha cria uma instância de DefaultRedirectStrategy, que é uma implementação padrão de RedirectStrategy fornecida pelo Spring Security. Esta estratégia é usada para redirecionar o usuário após uma autenticação bem-sucedida.

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication; // Obtém o token de autenticação OAuth2
        String email = token.getPrincipal().getAttributes().get("email").toString(); // Extrai o email do usuário autenticado a partir dos atributos do principal associado ao token
        if (userRepository.findUserByEmail(email).isPresent()) { // Verifica se um usuário com o email obtido já existe no banco de dados

        } else { // Se o usuário não existe, cria um novo usuário com base nas informações do token de autenticação
            User user = new User();
            user.setFirtsName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            
            // Cria uma lista de roles e adiciona uma role específica (nesse caso, com o ID 2)
            List<Role> roles = new ArrayList<>(); 
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user); // Salva o novo usuário no banco de dados
        }
        redirectStrategy.sendRedirect(request, response, "/" ); // Redireciona o usuário para a página inicial ("/")
    }
}
