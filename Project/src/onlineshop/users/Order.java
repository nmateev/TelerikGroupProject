package onlineshop.users;

import onlineshop.store.Product;
import onlineshop.shipping.Shipping;
import java.util.HashMap;

public class Order {
    private User user;
    private HashMap<Product, Integer> productAndQuantityOrdered;
    private Shipping shipping;
    private double totalCost;

    public Order(User user, Shipping shipping, double totalCost) {
        setUser(user);
        setProductAndQuantityOrdered(user.getCart().getProductAndQuantityOrdered());
        setShipping(shipping);
        setTotalCost(totalCost);
    }

    public User getUser() {
        return user;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public HashMap<Product, Integer> getProductAndQuantityOrdered() {
        return productAndQuantityOrdered;
    }

    /*Overriding the toString method so that when we print or get the order information as string it will show(hold)
     the whole information for the order*/
    @Override
    public String toString() {
        StringBuilder orderInfo = new StringBuilder();
        int countOfAllItems = 0;
        HashMap<Product, Integer> productAndQuantityOrdered = getProductAndQuantityOrdered();

        orderInfo.append("This order will be sent to ").append(this.getShipping().getNameForDelivery())
                .append(" at ").append(this.getShipping().getShippingAddress()).append(" and is due to be delivered in ").append(this.getShipping().getDeliveryTime())
                .append(" , phone number for delivery: ").append(this.getShipping().getPhoneNumberForDelivery())
                .append("\n\nItems ordered:\n\n");

        for (Product orderedProduct : productAndQuantityOrdered.keySet()) {

            String singlePrice = String.format("%.2f",orderedProduct.getPrice());

            orderInfo
                    .append(orderedProduct).append(",")
                    .append("  Item count: ").append(productAndQuantityOrdered.get(orderedProduct)).append(" ,   Single price: ").append(singlePrice).append(" lv.")
                    .append("\n");
            countOfAllItems += productAndQuantityOrdered.get(orderedProduct);
        }
        String totalPrice = String.format("%.2f",this.getTotalCost());

        orderInfo.append("\nNumber of ordered items: ")
                .append(countOfAllItems)
                .append("\n\nShipping cost: ").append(this.getShipping().getPrice()).append(" lv.")
                .append("\nTotal price with delivery costs: ").append(totalPrice).append(" lv.");
        return orderInfo.toString();
    }

    void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    private void setUser(User user) {
        this.user = user;
    }

    private void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    private void setProductAndQuantityOrdered(HashMap<Product, Integer> productAndQuantityOrdered) {
        this.productAndQuantityOrdered = productAndQuantityOrdered;
    }
}


