package onlineshop.users;


import onlineshop.shipping.Shipping;
import onlineshop.store.OnlineStore;
import onlineshop.store.CustomProduct;
import onlineshop.store.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class User implements Orderable {

    private String userName;
    private String userPassword;
    private String phoneNumber;
    private String address;
    Cart cart;
    ArrayList<Order> userCompletedOrders;

    public User() {

        setCart(new Cart());
        setUserCompletedOrders(new ArrayList<>());
    }

    public User(String userName, String userPassword) {
        setUserName(userName);
        setUserPassword(userPassword);
        setCart(new Cart());
        setUserCompletedOrders(new ArrayList<>());
    }

    public User(String userName, String userPassword, String phoneNumber, String address) {
        setUserName(userName);
        setUserPassword(userPassword);
        setPhoneNumber(phoneNumber);
        setAddress(address);
        setCart(new Cart());
        setUserCompletedOrders(new ArrayList<>());
    }

    public String getUserName() {
        return userName;
    }

    public Cart getCart() {
        return cart;
    }

    public ArrayList<Product> getItemsInCart() {
        return new ArrayList<>(this.getCart().getProductAndQuantityOrdered().keySet());
    }

    @Override
    public void addProductToCart(Product product, int quantity) {
        HashMap<Product, Integer> productAndQuantityOrdered =
                this
                        .getCart()
                        .getProductAndQuantityOrdered();

        if (Objects.equals(productAndQuantityOrdered.get(product), null)) {
            productAndQuantityOrdered.put(product, quantity);
        } else {
            int alreadyOrderedQuantity = productAndQuantityOrdered.get(product);
            productAndQuantityOrdered.put(product, (alreadyOrderedQuantity + quantity));
        }
    }


    @Override
    public void addCustomProductToCart(CustomProduct product, int quantity) {
        HashMap<Product, Integer> productAndQuantityOrdered =
                this
                        .getCart()
                        .getProductAndQuantityOrdered();

        if (Objects.equals(productAndQuantityOrdered.get(product), null)) {
            productAndQuantityOrdered.put(product, quantity);
        } else {
            int alreadyOrderedQuantity = productAndQuantityOrdered.get(product);
            productAndQuantityOrdered.put(product, (alreadyOrderedQuantity + quantity));
        }

    }

    @Override
    public void deleteProductFromCart(Product product) {

        //deleting the value for the product that was added to the cart and the wanted  quantity for the product
        this.getCart().getProductAndQuantityOrdered().remove(product);

    }

    @Override
    public void buyProductsFromCart(Shipping shipping, OnlineStore store) throws IOException {

        User user = this;
        store.processOrder(user, shipping);

    }

    public void addOrderToUserCompletedOrders(Order order) {
        getUserCompletedOrders().add(order);
    }

    ArrayList<Order> getUserCompletedOrders() {
        return userCompletedOrders;
    }

    String getUserPassword() {
        return userPassword;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    String getAddress() {
        return address;
    }


    private void setUserName(String userName) {
        this.userName = userName;
    }


    private void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    private void setAddress(String address) {
        this.address = address;
    }

    private void setCart(Cart cart) {
        this.cart = cart;
    }

    private void setUserCompletedOrders(ArrayList<Order> userCompletedOrders) {
        this.userCompletedOrders = userCompletedOrders;
    }
}