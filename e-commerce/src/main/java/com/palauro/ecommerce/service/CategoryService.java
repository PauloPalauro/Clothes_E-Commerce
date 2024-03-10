package com.palauro.ecommerce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.palauro.ecommerce.model.Category;
import com.palauro.ecommerce.repository.CategoryRepository;

@Service //Marca a classe CategoryService como um serviço do Spring
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll(); //Ele chama o método findAll() de CategoryRepository, que é fornecido pelo Spring Data JPA e busca todas as categorias no banco de dados.
    }

    public void addCategory(Category category) {
        categoryRepository.save(category); //Ele chama o método save() de CategoryRepository, que é fornecido pelo Spring Data JPA e salva a categoria especificada no banco de dados.
    }

    public void removeCategoryById(int id){
        categoryRepository.deleteById(id); //Ele chama o método deleteById() de CategoryRepository, que é fornecido pelo Spring Data JPA e remove a categoria com o ID especificado do banco de dados.
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id); //Ele chama o método findById() de CategoryRepository, que é fornecido pelo Spring Data JPA e busca a categoria com o ID especificado no banco de dados. 
    }

}
