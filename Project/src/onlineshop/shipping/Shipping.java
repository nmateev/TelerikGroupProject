package shipping;

public abstract class Shipping {
    private ShippingType type;

    // shipping price
    protected double price;

    //delivery time in hours
    private String deliveryTime;

    //shipping address
    private String shippingAddress;

    public void setType(ShippingType type) {
        this.type = type;
    }

    public abstract void setPrice(double price);

    public void setDeliveryTime(String deliveryTime){

        this.deliveryTime = deliveryTime;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingType getType() {
        return type;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public double getPrice() {
        return price;
    }
}
