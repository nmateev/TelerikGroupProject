package onlineshop.productdatabase;


import onlineshop.product.Category;
import onlineshop.product.Customisable;
import onlineshop.product.Product;
import onlineshop.product.Returnable;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProductDatabase implements LoadableDatabase, SearchableDatabase {
    ArrayList<Product> products;
    ArrayList<Returnable> returnableProducts;
    ArrayList<Customisable> customisableProducts;

    ProductDatabase() {
        setProducts(new ArrayList<>());
        setReturnableProducts(new ArrayList<>());
        setCustomisableProducts(new ArrayList<>());
    }

    /* this method loads the store's product database from a text file where the product data is structured as follows:
     name of product, brand, description of product, category, stock availability, price, store supplier,
     whether or not the product is customisable, description for customisation*/
    @Override
    public void loadProductDatabase() {

        File file = new File("Project\\files\\products-data.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while (!(Objects.equals(line = in.readLine(), null))) {
                String[] productData = line.split(", ");
               // Arrays.stream(productData).forEach(System.out::println);
                //to do create product objects and populate product lists
            }
        } catch (IOException e) {
            System.out.println("Well, this was unexpected. There seems to be a problem with the application.\n" +
                    "Please restart and try again");
        }
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

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public ArrayList<Returnable> getReturnableProducts() {
        return new ArrayList<>(returnableProducts);
    }

    public ArrayList<Customisable> getCustomisableProducts() {
        return new ArrayList<>(customisableProducts);
    }

    private void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    private void setReturnableProducts(ArrayList<Returnable> returnableProducts) {
        this.returnableProducts = returnableProducts;
    }

    private void setCustomisableProducts(ArrayList<Customisable> customisableProducts) {
        this.customisableProducts = customisableProducts;
    }

}
