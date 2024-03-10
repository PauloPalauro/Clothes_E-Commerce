package com.palauro.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity //Esta anotação marca a classe Category como uma entidade JPA, o que significa que ela será mapeada para uma tabela no banco de dados.
@Data //Esta anotação é do projeto Lombok e é usada para gerar automaticamente métodos como toString(), equals(), hashCode() e outros, economizando a escrita de código boilerplate.
public class Category {

    @Id //Marca o campo id como a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.AUTO) //  Esta anotação especifica como a chave primária será gerada automaticamente. Neste caso, GenerationType.AUTO indica que o provedor de persistência (como Hibernate) escolherá a estratégia de geração de chave primária adequada para o banco de dados. 
    @Column(name = "category_id") //Esta anotação é usada para mapear o nome da coluna no banco de dados
    private int id;
    
    private String name;
    
}
