package com.palauro.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Este arquivo é a classe principal da aplicação Spring Boot, responsável por iniciar a aplicação. 

@SpringBootApplication // Esta anotação é usada para marcar a classe como uma classe de aplicação Spring Boot. Ela habilita a autoconfiguração do Spring Boot, que detecta automaticamente configurações, beans e componentes na aplicação e configura-os conforme necessário.
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args); //Este trecho de código inicia a aplicação Spring Boot. Ele recebe a classe principal da aplicação (ECommerceApplication.class) e os argumentos de linha de comando (args) e inicia a aplicação Spring Boot.
	}

}
