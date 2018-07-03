package onlineshop.product;

import onlineshop.product.Product;
import java.util.ArrayList;

public interface SearchableDatabase {
    ArrayList<Product> searchByBrand(String brand);

    ArrayList<Product> searchByName(String name);

    ArrayList<Product> searchByPrice(String price);

    ArrayList<Product> searchBelowGivenPrice(String price);

    ArrayList<Product> searchAboveGivenPrice(String price);

    ArrayList<Product> searchByCategory(String category);

    ArrayList<Product> searchByDescription(String description);
}
