package com.palauro.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.palauro.ecommerce.global.GlobalData;
import com.palauro.ecommerce.model.Role;
import com.palauro.ecommerce.model.User;
import com.palauro.ecommerce.repository.RoleRepository;
import com.palauro.ecommerce.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController{

    //O Spring procura por um bean correspondente (no caso, uma instância de BCryptPasswordEncoder) e a injeta automaticamente na variável.
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear(); // Limpa o cart que está armazenado em GlobalData. Para garantir que o carrinho esteja vazio sempre que um usuário acessar a página de login, evitando que itens indesejados permaneçam no carrinho entre sessões de usuários diferentes.
        return "login";
    }

    @GetMapping("/register")
    public String resgisterGet(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException{ // HttpServletRequest request = contém informações sobre a solicitação HTTP feita pelo cliente. Ele inclui coisas como parâmetros de solicitação, cabeçalhos HTTP, método de solicitação (GET, POST, etc.) e outras informações relacionadas à solicitação HTTP.
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password)); //Codifica a senha do usuário usando o BCryptPasswordEncoder injetado anteriormente.
        List<Role> roles = new ArrayList<>(); //Cria uma lista de roles para o usuário. 
        roles.add(roleRepository.findById(2).get()); //Uma role é adicionada à lista de roles do usuário.
        user.setRoles(roles); //Define as roles do usuário no objeto user
        userRepository.save(user); //Salva o novo usuário no banco de dados.
        request.login(user.getEmail(), password); // Autentica o usuário recém-registrado. Isso é feito chamando o método login() do objeto request, passando o email e a senha do usuário como parâmetros.
        return "redirect:/index";
    }

}