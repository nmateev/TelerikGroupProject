package onlineshop.productdatabase;


import onlineshop.product.Category;
import onlineshop.product.Customisable;
import onlineshop.product.Product;
import onlineshop.product.Returnable;

import java.util.ArrayList;

public class ProductDatabase implements LoadableDatabase, SearchableDatabase {
    ArrayList<Product> products;
    ArrayList<Returnable> returnableProducts;
    ArrayList<Customisable> customisableProducts;

    @Override
    public void loadProductDatabase() {

    }

    @Override
    public ArrayList<Product> searchByBrand(String brand) {
        return null;
    }

    @Override
    public ArrayList<Product> searchByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Product> searchByPrice(double price) {
        return null;
    }

    @Override
    public ArrayList<Product> searchByCategory(Category category) {
        return null;
    }

    @Override
    public ArrayList<Product> searchByDescription(String description) {
        return null;
    }

    public ArrayList<Product> sortByAscendingPrice() {
        ArrayList<Product> sortedProducts = new ArrayList<>();
        return sortedProducts;
    }

    public ArrayList<Product> sortByDescendingPrice() {
        ArrayList<Product> sortedProducts = new ArrayList<>();
        return sortedProducts;
    }

}
