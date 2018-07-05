package onlineshop.users;

import onlineshop.shipping.Shipping;
import onlineshop.store.OnlineStore;
import onlineshop.store.CustomProduct;
import onlineshop.store.Product;

import java.io.IOException;

//Interface that when implemented will present to the user the possibility to order and interactWithUser with its stores cart
public interface Orderable {

    void addProductToCart(Product product, int quantity);

    void addCustomProductToCart(CustomProduct product, int quantity);

    void deleteProductFromCart(Product product);

    void buyProductsFromCart(Shipping shipping, OnlineStore store)throws IOException;
}
