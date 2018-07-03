package onlineshop.users;

import onlineshop.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> productsAndQuantities;
    private int itemCount;

    public Cart() {
        productsAndQuantities = new HashMap<>();
    }

    public Map<Product, Integer> getProductsAndQuantities() {
        return new HashMap<>(productsAndQuantities);
    }

    public void addToCart(Product product, int quantity) {
        productsAndQuantities.put(product,quantity);
    }

    public void removeFromCart(Product product) {
        productsAndQuantities.remove(product);
    }

    public int getItemCount() {
        for (Product product: productsAndQuantities.keySet()) {
            itemCount += productsAndQuantities.get(product);
        }
        return itemCount;
    }
}