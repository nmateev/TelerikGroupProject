package onlineshop.order;
import onlineshop.product.Product;
import onlineshop.shipping.ShippingType;
import onlineshop.users.User;

import java.util.ArrayList;

public class Order {
    private int totalCost;
    private ArrayList<Product> productList;
    private User user;
    private ShippingType shippingType;

}
