package com.palauro.ecommerce.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Interface UserDetails -> https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html
public class CustomUserDetails extends User implements UserDetails {

    public CustomUserDetails(User user){
        super(user);
    }

    /*
      Este método percorre as roles do usuário e converte cada uma delas em objetos GrantedAuthority,
       que são então retornados como uma coleção. Isso é útil para o Spring Security entender quais papéis um usuário possui e,
        assim, realizar a autorização corretamente no aplicativo.
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Este método é usado para obter as autoridades (papéis) do usuário. Ele retorna uma coleção de objetos GrantedAuthority, que representam os papéis atribuídos ao usuário.
        List<GrantedAuthority> authorityList = new ArrayList<>(); // Cria uma lista vazia para armazenar as autoridades do usuário.
        super.getRoles().forEach(role -> { // Chama o método getRoles() da classe pai para obter as roles do usuário. Em seguida, itera sobre cada role usando um forEach.
            authorityList.add(new SimpleGrantedAuthority(role.getName())); //Para cada role do usuário, cria um novo objeto SimpleGrantedAuthority com o nome da role e adiciona à lista de autoridades.
        });
        return authorityList;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() { //Este método indica se a conta do usuário está expirada. Neste caso, sempre retorna true, o que significa que a conta nunca expira.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //Este método indica se a conta do usuário está bloqueada. Neste caso, sempre retorna true, o que significa que a conta nunca está bloqueada.
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() { //Este método indica se as credenciais do usuário (senha) estão expiradas. Neste caso, sempre retorna true, o que significa que as credenciais nunca expiram.
        return true;
    }

    @Override
    public boolean isEnabled() { //Este método indica se a conta do usuário está habilitada. Neste caso, sempre retorna true, o que significa que a conta está sempre habilitada.
        return true;
    }

}
