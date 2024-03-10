package com.palauro.ecommerce.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.palauro.ecommerce.service.CustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    GoogleOauth2SuccessHandler googleOauth2SuccessHandler;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean //Esta anotação é usada para indicar ao Spring que o método retorna um objeto que deve ser gerenciado e disponibilizado para injeção de dependência em outras partes da aplicação.
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception { // Este método configura as regras de segurança para o aplicativo. Ele retorna um objeto SecurityFilterChain, que define a cadeia de filtros de segurança para a aplicação.
        http
               //Este trecho define as regras de autorização para as requisições HTTP.
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/shop/**", "/register", "/h2-console/**", "/images/**","/productImages/**", "/css/**").permitAll() //Ele permite que determinados URLs sejam acessados por qualquer pessoa (permitAll())
                        .requestMatchers("/admin/**").hasRole("ADMIN") //Enquanto outros URLs só podem ser acessados por usuários com o papel (role) "ADMIN". 
                        .anyRequest().authenticated()) //Qualquer outra requisição deve ser autenticada.

                
                //Este trecho configura o formulário de login.
                .formLogin(formlogin -> formlogin
                        .loginPage("/login") //Define a página de login como "/login".
                        .permitAll() //Permite que todos os usuários acessem esta página (permitAll()).
                        .failureUrl("/login?error= true") //Define a URL de redirecionamento em caso de falha de login.
                        .defaultSuccessUrl("/", true) //Define a URL de redirecionamento padrão após um login bem-sucedido .
                        .usernameParameter("email") //especifica os parâmetros de nome de usuário.
                        .passwordParameter("password")) //especifica os parâmetros de senha.


                //Este trecho configura o login com OAuth2.
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login") //Especificando a página de login como "/login" 
                        .successHandler(googleOauth2SuccessHandler)) //Manipulador de sucesso como googleOauth2SuccessHandler


                //Este trecho configura a funcionalidade de logout. 
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //Define a URL de logout como "/logout"
                        .logoutSuccessUrl("/login") //A URL de redirecionamento após o logout como "/login"
                        .invalidateHttpSession(true) //Invalida a sessão HTTP
                        .deleteCookies("JSESSIONID")) //Exclui os cookies

                .csrf(csrf -> csrf.disable()) //Este trecho desabilita a proteção CSRF (Cross-Site Request Forgery) 
                .headers(headers -> headers.frameOptions(frameoptions -> frameoptions.disable())); // desabilita a proteção contra ataques de clique em caixas (X-Frame-Options) para permitir a exibição de conteúdo em um iframe.

        return http.build(); 
    }

    @Bean
    //Retorna um bean BCryptPasswordEncoder, que é usado para codificar senhas de forma segura. Este bean é configurado como um bean gerenciado pelo Spring.
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Este método configura o AuthenticationManagerBuilder, que é usado para construir o gerenciador de autenticação. Ele define o customUserDetailService como o serviço a ser usado para carregar os detalhes do usuário durante a autenticação.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    //Este método configura a segurança da web, especificamente ignorando determinados padrões de URL para que eles não sejam protegidos pela segurança da aplicação. Isso significa que as solicitações correspondentes a esses padrões de URL não serão verificadas quanto à autenticação.
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**", "/productimages/**", "/css/**", "/js/**");
    }
}
