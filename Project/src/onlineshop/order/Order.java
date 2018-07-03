package onlineshop.order;

import onlineshop.shipping.Shipping;
import onlineshop.users.User;

public class Order {
    private User user;
    private double totalCost;

    public Order(User user, Shipping type, double totalCost, int itemCount) {
        setUser(user);
        this.totalCost = totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }
}
