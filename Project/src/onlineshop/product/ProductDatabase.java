package onlineshop.product;

import onlineshop.Supplier;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDatabase implements LoadableDatabase, SearchableDatabase {
    private static final String ERROR_MESSAGE_FOR_DATABASE_LOADING_PROBLEMS = "Well, this was unexpected. There seems to be a problem with the application.\n" +
            "Please restart and try again";
    private static final String ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE = "You should enter a numeric value. Try again";
    private static final String ERROR_MESSAGE_FOR_SEARCHING_BY_CATEGORY = "There is no such category. Try again.";
    ArrayList<Product> products;
    private ArrayList<Returnable> returnableProducts;
    private ArrayList<Customisable> customisableProducts;
    private ArrayList<Category> categories;

    public ProductDatabase() {
        setProducts(new ArrayList<>());
        setReturnableProducts(new ArrayList<>());
        setCustomisableProducts(new ArrayList<>());
        setCategories(new ArrayList<>());
    }

    /* this method loads the store's product database from a text file where the product data is structured as follows:
     name of product, brand, description of product, category, stock availability, price, store supplier,
     whether or not the product is customisable, description for customisation */
    @Override
    public void loadProductDatabase() {

        File file = new File("Project\\files\\products-data.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            int productId = 1;
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
                    CustomProduct customProduct = new CustomProduct(productId, name, brand, productDescription, category, stock, price, supplier, customisationDescription);
                    if (!Objects.equals(customProduct, null)) {
                        this.customisableProducts.add(customProduct);
                    }
                    StandardProduct standardProduct = new StandardProduct(productId, name, brand, productDescription, category, stock, price, supplier);
                    if (!Objects.equals(standardProduct, null)) {
                        this.products.add(standardProduct);
                    }
                } else {
                    StandardProduct standardProduct = new StandardProduct(productId, name, brand, productDescription, category, stock, price, supplier);
                    if (!Objects.equals(standardProduct, null)) {
                        this.products.add(standardProduct);
                        this.returnableProducts.add(standardProduct);
                    }
                }
                productId++;
            }
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE_FOR_DATABASE_LOADING_PROBLEMS);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    @Override
    public ArrayList<Product> searchByBrand(String brand) {

        List<Product> productsMatchingGivenBrand = getProducts()
                .stream()
                .filter(product -> product.getBrand().toLowerCase().equals(brand.toLowerCase()))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingGivenBrand;
    }

    @Override
    public ArrayList<Product> searchByName(String name) {
        String lowerCaseName = name.toLowerCase();
        List<Product> productsMatchingGivenName = getProducts()
                .stream()
                .filter(product -> product.getName().toLowerCase().equals(lowerCaseName))
                .collect(Collectors.toList());
        getProducts()
                .stream()
                .filter(product -> product.getName().toLowerCase().contains((lowerCaseName)))
                .forEach(productsMatchingGivenName::add);

        return (ArrayList<Product>) productsMatchingGivenName;
    }

    @Override
    public ArrayList<Product> searchByPrice(String price) {
        List<Product> productsMatchingGivenPrice = new ArrayList<>();
        try {
            double wantedPrice = Double.parseDouble(price.replaceAll(",", "."));
            productsMatchingGivenPrice = getProducts()
                    .stream()
                    .filter(product -> product.getPrice() == wantedPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE);
        }
        return (ArrayList<Product>) productsMatchingGivenPrice;
    }

    @Override
    public ArrayList<Product> searchBelowGivenPrice(String price) {
        List<Product> productsBelowGivenPrice = new ArrayList<>();
        try {
            double wantedPrice = Double.parseDouble(price.replaceAll(",", "."));
            productsBelowGivenPrice = getProducts()
                    .stream()
                    .filter(product -> product.getPrice() < wantedPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE);
        }
        return (ArrayList<Product>) productsBelowGivenPrice;
    }

    @Override
    public ArrayList<Product> searchAboveGivenPrice(String price) {
        List<Product> productsAboveGivenPrice = new ArrayList<>();
        try {
            double wantedPrice = Double.parseDouble(price.replaceAll(",", "."));
            productsAboveGivenPrice = getProducts()
                    .stream()
                    .filter(product -> product.getPrice() > wantedPrice)
                    .collect(Collectors.toList());
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE);
        }

        return (ArrayList<Product>) productsAboveGivenPrice;
    }

    @Override
    public ArrayList<Product> searchByCategory(String category) {
        List<Product> productsMatchingCategory = new ArrayList<>();
        try {
            Category searchedCategory = Category.valueOf(category.toUpperCase());
            productsMatchingCategory = getProducts()
                    .stream()
                    .filter(product -> product.getCategoryType().equals(searchedCategory))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException iae) {
            System.out.println(ERROR_MESSAGE_FOR_SEARCHING_BY_CATEGORY);
        }
        return (ArrayList<Product>) productsMatchingCategory;
    }

    @Override
    public ArrayList<Product> searchByDescription(String description) {
        String lowerCaseDescription = description.toLowerCase();
        List<Product> productsMatchingDescription = getProducts()
                .stream()
                .filter(product -> product.getDescription().toLowerCase().contains(lowerCaseDescription))
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

    public ArrayList<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void showDatabaseSupportedCategories() {
        StringBuilder categoriesBuilder = new StringBuilder();
        getCategories()
                .forEach(category -> categoriesBuilder.append(category).append(", "));

        categoriesBuilder.delete(categoriesBuilder.length() - 2, categoriesBuilder.length());

        System.out.println(categoriesBuilder);

    }

    public void addStandardProductToDatabase(StandardProduct product) {
        if (!Objects.equals(product, null)) {
            this.products.add(product);
            this.returnableProducts.add(product);
        }
    }

    public void addCustomProductToDatabase(CustomProduct product) {
        if (!Objects.equals(product, null)) {
            this.products.add(product);
            this.customisableProducts.add(product);
        }
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

    private void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        this.categories.add(Category.FOODS);
        this.categories.add(Category.DRINKS);
        this.categories.add(Category.CLOTHES);
        this.categories.add(Category.SHOES);
        this.categories.add(Category.ACCESSORIES);
        this.categories.add(Category.ELECTRONICS);
    }
}
