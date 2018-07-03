package onlineshop.product;

import java.util.ArrayList;

//Interface which marks the functionality which the database that implements the SearchableDatabase interface will support
public interface SearchableDatabase {
    ArrayList<Product> searchByBrand(String brand);

    ArrayList<Product> searchByName(String name);

    ArrayList<Product> searchByPrice(String price);

    ArrayList<Product> searchBelowGivenPrice(String price);

    ArrayList<Product> searchAboveGivenPrice(String price);

    ArrayList<Product> searchByCategory(String category);

    ArrayList<Product> searchByDescription(String description);
}
