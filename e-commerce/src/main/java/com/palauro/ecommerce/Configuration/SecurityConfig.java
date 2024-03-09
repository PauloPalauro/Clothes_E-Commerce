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

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/shop/**", "/register", "/h2-console/**", "/images/**",
                                "/productImages/**", "/css/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(formlogin -> formlogin
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error= true")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("email")
                        .passwordParameter("password"))

                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .successHandler(googleOauth2SuccessHandler))

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))

                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameoptions -> frameoptions.disable()));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**", "/productimages/**", "/css/**",
                "/js/**");
    }
}
