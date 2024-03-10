package com.palauro.ecommerce.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "roles") //Esta anotação é usada para especificar o nome da tabela no banco de dados.
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty //Esta anotação é da biblioteca de validação de Bean Validation e é usada para garantir que o campo name não seja vazio.
    @Column(nullable = false, unique = true)//Esta anotação é usada para especificar as propriedades da coluna no banco de dados. nullable = false indica que o campo name não pode ser nulo, e unique = true indica que o valor do campo name deve ser único.
    private String name;

    @ManyToMany(mappedBy = "roles") //Esta anotação é usada para definir o relacionamento muitos-para-muitos entre as entidades Role e User. Ela indica que a entidade Role é o lado inverso do relacionamento e que o campo roles na classe User é o lado proprietário do relacionamento.
    private List<User> users; 
 
    
}
