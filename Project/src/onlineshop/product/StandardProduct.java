package onlineshop.product;

import onlineshop.Supplier;
import onlineshop.users.User;

public class StandardProduct extends Product implements Returnable {

    public StandardProduct(String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        super(name, brand, description, categoryType, stock, price, supplier);
    }

    @Override
    public void giveMoneyBack(User user) {

    }

    @Override
    public void increaseStockAfterReturn(Product product, int quantity) {

    }
}

