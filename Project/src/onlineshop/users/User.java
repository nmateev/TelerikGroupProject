package onlineshop.users;

import onlineshop.OnlineStore;
import onlineshop.order.Order;
import onlineshop.product.CustomProduct;
import onlineshop.product.Customisable;
import onlineshop.product.Product;
import onlineshop.shipping.Shipping;
import onlineshop.shipping.ShippingType;

public abstract class User  {
    private String userName;
    private String password;
    private String phone;
    private String address;
    private Shipping type;
    private Cart cart;

    protected User() {
        setCart(new Cart());
    }

    protected User(String userName, String address, String phone,
                   String password,Cart cart) {
        setPassword(password);
        setUserName(userName);
        setPhone(phone);
        setAddress(address);
        setCart(cart);
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public void giveOrder(OnlineStore store, Shipping shipping) {
        double totalCost = 0;
        int itemCount = this.getCart().getItemCount();
        for (Product product : this.getCart().getProductsAndQuantities().keySet()) {
            if (product instanceof CustomProduct) {
                totalCost += (product.getPrice() + ((CustomProduct) product).COST_OF_CUSTOMISATION)
                        * this.getCart().getProductsAndQuantities().get(product);
            }
            else {
                totalCost += product.getPrice() * this.getCart().getProductsAndQuantities().get(product);
            }
        }

        switch (shipping.getType()) {
            case FREE:
                totalCost += shipping.getPrice();
                break;
            case STANDART:
                totalCost += shipping.getPrice();
                break;
            case EXPRESS:
                totalCost += shipping.getPrice();
                break;
        }

        if (this instanceof VipUser) {
            switch (((VipUser) this).getVipType()) {
                case SILVER:
                    totalCost -= totalCost * ((VipUser) this).SILVER_VIP_USER_DISCOUNT;
                    break;
                case GOLD:
                    totalCost -= totalCost * ((VipUser) this).GOLD_VIP_USER_DISCOUNT;
                    break;
                case PLATINUM:
                    totalCost -= totalCost * ((VipUser) this).PLATINUM_VIP_USER_DISCOUNT;
                    break;
            }
        }
        Order userOrder = new Order(this,shipping,totalCost,itemCount);
        store.getAllOrders().add(userOrder);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ShippingType getType() {
        return type.getType();
    }

    public void setType(Shipping type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}