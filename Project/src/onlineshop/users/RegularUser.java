package onlineshop.users;

import onlineshop.order.Order;
import onlineshop.product.Product;

public class RegularUser extends User {

    protected RegularUser(String userName, String address, String phone, String password, Cart cart) {
        super(userName, address, phone, password, new Cart());
    }
}
