package com.palauro.ecommerce.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String firtsName;

    private String lastName;

    @NotEmpty
    @Email(message = "{errors.invalid_email}") //Esta anotação é usada para garantir que o valor do campo email seja um endereço de email válido, seguindo a sintaxe padrão de um endereço de email. Se o email não for válido, a mensagem definida em message será usada para informar o usuário sobre o erro.
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)//cascade = CascadeType.MERGE: Esta configuração indica que as operações merge realizadas no usuário também devem ser aplicadas às roles associadas a ele. fetch = FetchType.EAGER = Esta configuração indica que as roles associadas a um usuário devem ser carregadas automaticamente (fetch) quando o usuário for recuperado do banco de dados. 
    @JoinTable( //Usada para especificar os detalhes da tabela intermediária que mapeia o relacionamento muitos-para-muitos
        name = "user_role", //Especifica o nome da tabela intermediária no banco de dados
        joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}, //Especifica as colunas da chave estrangeira que mapeiam o lado proprietário do relacionamento. Neste caso, name = "USER_ID" especifica o nome da coluna na tabela user_role que contém as chaves estrangeiras para a tabela User, e referencedColumnName = "ID" especifica o nome da coluna na tabela User que é referenciada pela chave estrangeira.
        inverseJoinColumns = {@JoinColumn(name ="ROLE_ID", referencedColumnName = "ID")} //Especifica as colunas da chave estrangeira que mapeiam o lado inverso do relacionamento. Neste caso, name ="ROLE_ID" especifica o nome da coluna na tabela user_role que contém as chaves estrangeiras para a tabela Role, e referencedColumnName = "ID" especifica o nome da coluna na tabela Role que é referenciada pela chave estrangeira.
    )
    private List<Role> roles;

    public User(User user){
        this.firtsName = user.getFirtsName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User(){   
    }
    
}
