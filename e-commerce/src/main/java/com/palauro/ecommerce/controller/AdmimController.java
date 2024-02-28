package com.palauro.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdmimController {

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(){
        return "categories";
    }    

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(){
        return "categoriesAdd";
    }
}
