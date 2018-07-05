package onlineshop.store;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDatabase implements LoadableDatabase, SearchableDatabase {
    private static final String ERROR_MESSAGE_FOR_DATABASE_LOADING_PROBLEMS = "Well, this was unexpected. There seems to be a problem with the application.\n" +
            "Please restart and try again";
    private static final String ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE = "You should enter a numeric value. Try again";
    private static final String ERROR_MESSAGE_FOR_SEARCHING_BY_CATEGORY = "There is no such category. Try again.";
    ArrayList<Product> products;
    ArrayList<Returnable> returnableProducts;
    private ArrayList<Customisable> customisableProducts;
    private ArrayList<Category> categories;

    public ProductDatabase() {
        setProducts(new ArrayList<>());
        setReturnableProducts(new ArrayList<>());
        setCustomisableProducts(new ArrayList<>());
        setCategories(new ArrayList<>());
    }

    /* this method loads the store's store database from a text file where the store data is structured as follows:
     name of store, brand, description of store, category, stock availability, price, store supplier,
     whether or not the store is customisable, description for customisation */
    @Override
    public void loadProductDatabase() {
        String separator = System.getProperty("file.separator");
        File file = new File("Project" + separator + "files" + separator + "products-data.txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;

            /*Product id serves as identification of the store on store level and it is a unique value for each store.
             In the case when a store can be customised, it has the same id as the standard store because the store represents
              the same object and the only difference is that the store can be customised but it can be purchased from the store in
              its original state and in  it's customised state*/
            int productId = 1;
            while (!(Objects.equals(line = in.readLine(), null))) {
                String[] productData = line.split(", ");
                String name = productData[0];
                String brand = productData[1];
                String productDescription = productData[2];
                Category category = null;
                String categoryType = productData[3];
                switch (categoryType) {
                    case "FOODS":
                        category = Category.FOODS;
                        break;
                    case "DRINKS":
                        category = Category.DRINKS;
                        break;
                    case "CLOTHES":
                        category = Category.CLOTHES;
                        break;
                    case "SHOES":
                        category = Category.SHOES;
                        break;
                    case "ACCESSORIES":
                        category = Category.ACCESSORIES;
                        break;
                    case "ELECTRONICS":
                        category = Category.ELECTRONICS;
                        break;
                }
                int stock = Integer.parseInt(productData[4]);
                double price = Double.parseDouble(productData[5]);
                String nameOfSupplier = productData[6];
                Supplier supplier = new Supplier(nameOfSupplier);
                String customisationOption = productData[7];
                boolean isCustomisable = customisationOption.equals("yes");

                /*Checking to see if the product is customisable and if this condition is met then we add the product to both the
                 * products list and customisableProducts list*/
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

                    /*Handling the case when the product is not customisable and when this condition is met then
                     * we add it to the products list and to the returnableProducts list(only standard products can be returned
                     * to the store not customised)*/
                    StandardProduct standardProduct = new StandardProduct(productId, name, brand, productDescription, category, stock, price, supplier);
                    if (!Objects.equals(standardProduct, null)) {
                        this.products.add(standardProduct);
                        this.returnableProducts.add(standardProduct);
                    }
                }
                //Increase the product id value so after each product read from the file the value will be unique
                productId++;
            }
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE_FOR_DATABASE_LOADING_PROBLEMS);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

    }

    /* this method handles the case when the user wants to search the store's product database and wants to find products
     to buy that match given brand. In order to give the user a more pleasant user experience we handle the possibility
     when the user types different case of letters and convert the passed argument to lower case */
    @Override
    public ArrayList<Product> searchByBrand(String brand) {

        List<Product> productsMatchingGivenBrand = getProducts()
                .stream()
                .filter(product -> product.getBrand().toLowerCase().equals(brand.toLowerCase()))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingGivenBrand;
    }

    /* this method handles the case when the user wants to search the store's product database and wants to find products
         to buy that match given name. We stream trough the databases and first check if the name passed as argument equals
          completely our products's name and after that we check if the products name contains the searched name in order not
          to leave the user with zero results and show similar options*/
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

    /*searching trough the products database and check if there are products that match the exact same
     price passed as an argument. The method takes a string as parameter in order to support both the comma and dot
     separator for the passed value*/
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

            //handling possible exception when the user passes some value that is not numeric
            System.out.println(ERROR_MESSAGE_FOR_SEARCHING_BY_PRICE);
        }
        return (ArrayList<Product>) productsMatchingGivenPrice;
    }

    /*searching trough the products database and check if there are products that match the given criteria
    to be more precise if there are products that are cheaper than the  price passed as an argument*/
    @Override
    public ArrayList<Product> searchBelowGivenPrice(String price) throws NumberFormatException {


        double wantedPrice = Double.parseDouble(price.replaceAll(",", "."));
        List<Product> productsBelowGivenPrice = getProducts()
                .stream()
                .filter(product -> product.getPrice() < wantedPrice)
                .collect(Collectors.toList());

        return (ArrayList<Product>) productsBelowGivenPrice;
    }

    /*searching trough the products database and check if there are products that match the given criteria - if there
    are products that are more expensive than the  price passed as an argument*/
    @Override
    public ArrayList<Product> searchAboveGivenPrice(String price) throws NumberFormatException {

        double wantedPrice = Double.parseDouble(price.replaceAll(",", "."));
        List<Product> productsAboveGivenPrice = getProducts()
                .stream()
                .filter(product -> product.getPrice() > wantedPrice)
                .collect(Collectors.toList());

        return (ArrayList<Product>) productsAboveGivenPrice;
    }

    /*handles the database capability to be searched by category and gets the products that are matching the searched
     category*/
    @Override
    public ArrayList<Product> searchByCategory(String category) throws IllegalArgumentException {
        List<Product> productsMatchingCategory;

        Category searchedCategory = Category.valueOf(category.toUpperCase());
        productsMatchingCategory = getProducts()
                .stream()
                .filter(product -> product.getCategoryType().equals(searchedCategory))
                .collect(Collectors.toList());

        return (ArrayList<Product>) productsMatchingCategory;
    }

    /*this method searches the database products and checks whether the store description  contains the one that the
     * user is searching */
    @Override
    public ArrayList<Product> searchByDescription(String description) {
        String lowerCaseDescription = description.toLowerCase();
        List<Product> productsMatchingDescription = getProducts()
                .stream()
                .filter(product -> product.getDescription().toLowerCase().contains(lowerCaseDescription))
                .collect(Collectors.toList());
        return (ArrayList<Product>) productsMatchingDescription;
    }

    //gets all products from the database and sorts them by price in ascending order from lower to bigger
    public ArrayList<Product> sortByAscendingPrice() {
        List<Product> sortedAscendingOrder = getProducts()
                .stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return (ArrayList<Product>) sortedAscendingOrder;
    }

    //gets all products from the database and sorts them by price in descending order from bigger to lower
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

    //adds standard product to the database but by doing so with a method we validate if the created product does not equal null
    public void addStandardProductToDatabase(StandardProduct product) {
        if (!Objects.equals(product, null)) {
            this.products.add(product);
            this.returnableProducts.add(product);
        }
    }

    //adds custom  product to the database but by doing so with a method we validate if the created product does not equal null
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

