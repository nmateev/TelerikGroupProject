package onlineshop.users;

import onlineshop.store.Product;

import java.util.HashMap;

public class Cart {

    HashMap<Product, Integer> productAndQuantityOrdered;

    Cart() {
        setProductAndQuantityOrdered(new HashMap<>());
    }

    public HashMap<Product, Integer> getProductAndQuantityOrdered() {
        return productAndQuantityOrdered;
    }

    private void setProductAndQuantityOrdered(HashMap<Product, Integer> productAndQuantityOrdered) {
        this.productAndQuantityOrdered = productAndQuantityOrdered;
    }
}