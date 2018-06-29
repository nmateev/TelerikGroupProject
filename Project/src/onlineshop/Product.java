package onlineshop;

import onlineshop.Supplier;

public abstract class Product {
    int id;
    String name;
    String brand;
    String description;
    product.Category categoryType;
    int stock;
    double price;
    Supplier supplier;

}
