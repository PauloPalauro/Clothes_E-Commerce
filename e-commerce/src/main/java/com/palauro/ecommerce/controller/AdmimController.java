package com.palauro.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.palauro.ecommerce.dto.ProductDTO;
import com.palauro.ecommerce.model.Category;
import com.palauro.ecommerce.model.Product;
import com.palauro.ecommerce.service.CategoryService;
import com.palauro.ecommerce.service.ProductService;

@Controller
public class AdmimController {

    // System.getProperty("user.dir") = método que retorna o diretório de trabalho atual do usuário. 
    //Concatena o caminho "/src/main/resources/static/productImages" ao diretório de trabalho atual.
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    // Injeção de dependências dos serviços necessários
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    //Método será chamado quando uma requisição GET for feita para a URL "/admin"
    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome"; //Retorna uma String, que é o nome da view que será renderizada.
    }

    
    @GetMapping("/admin/categories")
    public String getCategories(Model model) { //Recebe um objeto Model como parâmetro, que é usado para adicionar atributos à view. É uma classe que permite que você passe dados do controlador para a visualização. Ele atua como um container para os objetos que você deseja disponibilizar na camada de visualização
        model.addAttribute("categories", categoryService.getAllCategory()); //Adiciona um atributo chamado "categories" ao objeto Model
        return "categories"; 
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model) {
        model.addAttribute("category", new Category()); //Método adiciona um objeto Category vazio ao objeto Model com o nome "category". Isso é comum para inicializar um formulário de adição de categoria com campos vazios.
        return "categoriesAdd"; 
    }

    //Método será chamado quando uma requisição POST for feita para a URL "/admin/categories/add"
    @PostMapping("/admin/categories/add") 
    public String postCategoriesAdd(@ModelAttribute("category") Category category) { // @ModelAttribute, que é usada para vincular os dados do formulário HTML aos objetos Java. O objeto category será preenchido com os dados do formulário HTML enviado pela solicitação, e o nome "category" será usado para identificar os dados na visualização
        categoryService.addCategory(category); //Chama o serviço addCategory() passando o objeto Category como argumento.
        return "redirect:/admin/categories"; //Retorna uma redirecionamento para a URL "/admin/categories" após o processamento da requisição. Isso geralmente é feito para evitar o reenvio do formulário quando o usuário atualiza a página.
    }

    @GetMapping("/admin/categories/delete/{id}") 
    public String deleteCategory(@PathVariable int id) { //Método recebe o parâmetro id que é o identificador da categoria a ser excluída.
        categoryService.removeCategoryById(id); 
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id); //Método chama o serviço getCategoryById() passando o id da categoria como argumento. O retorno é encapsulado em um Optional, que é uma maneira de lidar com possíveis valores nulos.
        if (category.isPresent()) { //Contém um valor presente
            model.addAttribute("category", category.get()); // Adicionada ao modelo e
            return "categoriesAdd"; //página "categoriesAdd" será renderizada
        } else {
            return "404"; //Se não existir, a página "404" será retornada
        }
    }

    // Product Section
    @GetMapping("/admin/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String productsAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO()); //Este método adiciona um objeto ProductDTO vazio ao objeto Model com o nome "productDTO". ProductDTO provavelmente é um DTO (Data Transfer Object) que contém os campos necessários para adicionar um novo produto.    
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
            @RequestParam("productImage") MultipartFile file, //@RequestParam("productImage") é usado para extrair o arquivo de imagem enviado como parte da solicitação HTTP POST
            @RequestParam("imgName") String imgName) throws IOException { // @RequestParam("imgName") é usado para extrair o nome da imagem

        //Define os atributos do objeto Product com base nos valores fornecidos no objeto ProductDTO recebido como parâmetro
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;

        if (!file.isEmpty()) { //verifica se o arquivo de imagem do produto foi enviado. Se um arquivo for enviado, ele é salvo no diretório de upload. Caso contrário, o nome da imagem é obtido do parâmetro imgName.
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPAth = Paths.get(uploadDir, imageUUID); //Cria um caminho completo para o arquivo de imagem no diretório de upload.
            Files.write(fileNameAndPAth, file.getBytes()); // Escreve os bytes do arquivo de imagem no caminho especificado.
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get(); //Obtém o produto a ser atualizado chamando o serviço getProductById() do objeto productService. O método get() é chamado para obter o produto encapsulado no Optional.
        ProductDTO productDTO = new ProductDTO(); //Cria um novo objeto ProductDTO para representar os dados do produto que serão exibidos no formulário de atualização.
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }
}
