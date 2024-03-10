package com.palauro.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.palauro.ecommerce.global.GlobalData;
import com.palauro.ecommerce.model.Product;
import com.palauro.ecommerce.service.ProductService;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        GlobalData.cart.add(productService.getProductById(id).get()); //Adiciona o produto ao carrinho de compras.
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());//Adiciona um atributo chamado "total" ao objeto Model. O valor deste atributo é a soma dos preços de todos os produtos no carrinho.
        model.addAttribute("cart", GlobalData.cart); //O valor deste atributo é a lista cart armazenada em GlobalData
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @GetMapping("/warning")
    public String warning() {
        return "warning";
    }

    @GetMapping("/orderPlaced")
    public String order() {
        return "orderPlaced";
    }


}
