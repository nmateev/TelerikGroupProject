package onlineshop.productdatabase;


import onlineshop.product.Category;
import onlineshop.product.Product;

import java.util.ArrayList;

public interface SearchableDatabase {
    ArrayList<Product> searchByBrand(String brand);

    ArrayList<Product> searchByName(String name);

    ArrayList<Product> searchByPrice(double price);

    ArrayList<Product> searchByCategory(Category category);

    ArrayList<Product> searchByDescription(String description);
}
