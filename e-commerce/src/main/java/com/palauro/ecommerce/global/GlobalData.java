package com.palauro.ecommerce.global;

import java.util.ArrayList;
import java.util.List;
import com.palauro.ecommerce.model.Product;

public class GlobalData {

    public static List<Product> cart;
    static{
        cart = new ArrayList<Product>();
    }

    
}
