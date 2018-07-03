package onlineshop;

import onlineshop.order.Order;
import onlineshop.users.User;

import java.util.ArrayList;

public class OnlineStore {
    private ArrayList<Order> allOrders = new ArrayList<>();
    private ArrayList<User> registeredUsers = new ArrayList<>();

    public ArrayList<Order> getAllOrders() {
        return allOrders;
    }
}


