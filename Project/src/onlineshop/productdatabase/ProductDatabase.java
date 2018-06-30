package onlineshop.productdatabase;

import onlineshop.Supplier;
import onlineshop.product.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDatabase implements LoadableDatabase, SearchableDatabase {
    ArrayList<Product> products;
    ArrayList<Returnable> returnableProducts;
    ArrayList<Customisable> customisableProducts;

    public ProductDatabase() {
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
                String name = productData[0];
                String brand = productData[1];
                String productDescription = productData[2];
                Category category = null;
                String categoryType = productData[3];
                switch (categoryType) {
                    case "FOODS": category = Category.FOODS;
                        break;
                    case "DRINKS": category = Category.DRINKS;
                        break;
                    case "CLOTHES": category = Category.CLOTHES;
                        break;
                    case "SHOES": category = Category.SHOES;
                        break;
                    case "ACCESSORIES": category = Category.ACCESSORIES;
                        break;
                    case "ELECTRONICS": category = Category.ELECTRONICS;
                        break;
                }
                int stock = Integer.parseInt(productData[4]);
                double price = Double.parseDouble(productData[5]);
                String nameOfSupplier = productData[6];
                Supplier supplier = new Supplier(nameOfSupplier);
                String customisationOption = productData[7];
                boolean isCustomisable = customisationOption.equals("yes");
                if (isCustomisable) {
                    String customisationDescription = productData[8];
                    CustomProduct customProduct = new CustomProduct(name, brand, productDescription, category, stock, price, supplier, customisationDescription);
                    this.customisableProducts.add(customProduct);
                    StandardProduct standardProduct = new StandardProduct(name, brand, productDescription, category, stock, price, supplier);
                    this.products.add(standardProduct);
                } else {
                    StandardProduct standardProduct = new StandardProduct(name, brand, productDescription, category, stock, price, supplier);
                    this.products.add(standardProduct);
                    this.returnableProducts.add(standardProduct);
                }

            }
        } catch (IOException e) {
            System.out.println("Well, this was unexpected. There seems to be a problem with the application.\n" +
                    "Please restart and try again");
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    @Override
    public ArrayList<Product> searchByBrand(String brand) {
        List<Product> productsMatchingGivenBrand = getProducts()
                .stream()
                .filter(product -> product.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingGivenBrand;
    }

    @Override
    public ArrayList<Product> searchByName(String name) {
        String lowerCaseName = name.toLowerCase();
        List<Product> productsMatchingGivenName = getProducts()
                .stream()
                .filter(product -> product.getName().equals(lowerCaseName.toLowerCase()))
                .collect(Collectors.toList());
        getProducts()
                .stream()
                .filter(product -> product.getName().contains((lowerCaseName)))
                .forEach(productsMatchingGivenName::add);

        return (ArrayList<Product>) productsMatchingGivenName;
    }

    @Override
    public ArrayList<Product> searchByPrice(double price) {
        List<Product> productsMatchingGivenPrice = getProducts()
                .stream()
                .filter(product -> product.getPrice() == price)
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingGivenPrice;
    }

    @Override
    public ArrayList<Product> searchBelowGivenPrice(double price) {
        List<Product> productsBelowGivenPrice = getProducts()
                .stream()
                .filter(product -> product.getPrice() < price)
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsBelowGivenPrice;
    }

    @Override
    public ArrayList<Product> searchAboveGivenPrice(double price) {
        List<Product> productsBelowGivenPrice = getProducts()
                .stream()
                .filter(product -> product.getPrice() > price)
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsBelowGivenPrice;
    }

    @Override
    public ArrayList<Product> searchByCategory(Category category) {
        List<Product> productsMatchingCategory = getProducts()
                .stream()
                .filter(product -> product.getCategoryType().equals(category))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingCategory;
    }

    @Override
    public ArrayList<Product> searchByDescription(String description) {
        String lowerCaseDescription = description.toLowerCase();
        List<Product> productsMatchingDescription = getProducts()
                .stream()
                .filter(product -> product.getDescription().contains(lowerCaseDescription))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingDescription;
    }

    public ArrayList<Product> sortByAscendingPrice() {
        List<Product> sortedAscendingOrder = getProducts()
                .stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return (ArrayList<Product>) sortedAscendingOrder;
    }

    public ArrayList<Product> sortByDescendingPrice() {
        List<Product> sortedDescendingOrder = getProducts()
                .stream()
                .sorted((firstProduct, secondProduct) -> Double.compare(secondProduct.getPrice(), firstProduct.getPrice()))
                .collect(Collectors.toList());
        return (ArrayList<Product>) sortedDescendingOrder;
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
