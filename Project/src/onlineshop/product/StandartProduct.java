package onlineshop.product;

import onlineshop.Supplier;

public class StandartProduct extends Product {

    StandartProduct( String name, String brand, String description, Category categoryType, int stock, double price, Supplier supplier) {
        super( name, brand, description, categoryType, stock, price, supplier);
    }
}

