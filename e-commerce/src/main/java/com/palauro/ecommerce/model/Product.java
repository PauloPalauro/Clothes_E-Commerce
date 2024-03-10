package com.palauro.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY) // indica que a associação entre as entidades Product e Category é muitos-para-um. fetch = FetchType.LAZY = esta configuração especifica que o carregamento da entidade Category será adiado até que seja acessada pela primeira vez. Isso significa que a categoria não será carregada automaticamente junto com o produto quando uma consulta ao produto for feita, o que pode melhorar o desempenho se a categoria não for sempre necessária.
    @JoinColumn(name = "category_id", referencedColumnName ="category_id" ) //Esta anotação é usada para especificar as colunas da chave estrangeira que mapeia a associação. name = "category_id" especifica o nome da coluna na tabela Product que contém as chaves estrangeiras para a tabela Category. referencedColumnName ="category_id" especifica o nome da coluna na tabela Category que é referenciada pela chave estrangeira.
    private Category category;

    private double price;
    private double weight;
    private String description;
    private String imageName;

}
