package onlineshop.product;

import onlineshop.Supplier;

import java.util.ArrayList;

public class StandardProduct extends Product implements Returnable {

    private static final String ERROR_MESSAGE_FOR_RETURNING_PRODUCT = "There seems to be a problem with the return of the product. Please try again.";

    public StandardProduct(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        super(id, name, brand, description, categoryType, stock, price, supplier);
    }


    @Override
    public void increaseStockAfterReturn(int quantityReturned, ProductDatabase database) {
        try {
            Product returnedProduct = this;
            int id = returnedProduct.getId();
            int currentStockOfProduct;
            ArrayList<Product> allProducts = database.products;
            for (Product allProduct : allProducts) {
                if (allProduct.getId() == id) {
                    currentStockOfProduct = allProduct.getStock();
                    allProduct.setStock(currentStockOfProduct + quantityReturned);
                    break;
                }
            }
        } catch (NullPointerException npe) {
            System.out.println(ERROR_MESSAGE_FOR_RETURNING_PRODUCT);
        }
    }
}

