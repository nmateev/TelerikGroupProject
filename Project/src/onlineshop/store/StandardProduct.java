package onlineshop.store;

import java.util.ArrayList;

public class StandardProduct extends Product implements Returnable {

    private static final String ERROR_MESSAGE_FOR_RETURNING_PRODUCT = "There seems to be a problem with the return of the store. Please try again.";

    public StandardProduct(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        super(id, name, brand, description, categoryType, stock, price, supplier);
    }

    //method that handles the return of a product which implements Returnable interface
    @Override
    public void increaseStockAfterReturn(int quantityReturned, OnlineStore store) {
        try {
            Product returnedProduct = this;
            int id = returnedProduct.getId();
            int currentStockOfProduct;
            ArrayList<Product> allProducts = store.getDatabase().products;
            boolean isProductInTheSystem = false;

            /*checks if the store's database has the product and if it is in the database we get the current stock of the
           product and add the quantity that is returned*/
            for (Product product : allProducts) {
                if (product.getId() == id) {
                    isProductInTheSystem = true;
                    currentStockOfProduct = product.getStock();
                    product.setStock(currentStockOfProduct + quantityReturned);
                    break;
                }
            }

            /*handles the situation that the store has no longer this product in the its database and creates a new
            product with the returned quantity*/
            if (!isProductInTheSystem) {
                returnedProduct.setStock(quantityReturned);
                store.getDatabase().products.add(returnedProduct);
                store.getDatabase().returnableProducts.add((StandardProduct) returnedProduct);
            }
        } catch (NullPointerException npe) {
            System.out.println(ERROR_MESSAGE_FOR_RETURNING_PRODUCT);
        }
    }
}