package com.palauro.ecommerce.global;

import java.util.ArrayList;
import java.util.List;
import com.palauro.ecommerce.model.Product;

/* 
    Esta classe serve como um local centralizado para armazenar e acessar dados
    globais relacionados ao carrinho de compras. Como o campo cart é estático,
    ele pode ser acessado de qualquer lugar do código sem a necessidade de
    instanciar um objeto GlobalData.
 */

public class GlobalData {

    public static List<Product> cart;

    //Uma nova instância de ArrayList<Product> é criada e atribuída a cart. Isso garante que a lista esteja pronta para uso quando o aplicativo começar a ser executado.
    static {
        cart = new ArrayList<Product>();
    }

}
