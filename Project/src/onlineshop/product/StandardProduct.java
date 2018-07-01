package onlineshop.product;

import onlineshop.Supplier;

public class StandardProduct extends Product implements Returnable {

    public StandardProduct(int id, String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        super(id,name, brand, description, categoryType, stock, price, supplier);
    }


    @Override
    public void increaseStockAfterReturn(Product product, int quantity) {

    }
}

