package onlineshop.shipping;

public abstract class Shipping {

    Shipping(String nameForDelivery, String phoneNumberForDelivery, String shippingAddress, double price, String deliveryTime) {
        setNameForDelivery(nameForDelivery);
        setPhoneNumberForDelivery(phoneNumberForDelivery);
        setShippingAddress(shippingAddress);
        setPrice(price);
        setDeliveryTime(deliveryTime);
    }

    ShippingType type;
    private double price;
    private String deliveryTime;


    private String nameForDelivery;
    private String phoneNumberForDelivery;
    private String shippingAddress;

    public ShippingType getTypeOfShipping() {
        return type;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getNameForDelivery() {
        return nameForDelivery;
    }

    public String getPhoneNumberForDelivery() {
        return phoneNumberForDelivery;
    }

    public double getPrice() {
        return price;
    }

    private void setDeliveryTime(String deliveryTime) {

        this.deliveryTime = deliveryTime;
    }

    abstract void setTypeOfShipping();

    private void setPrice(double price) {
        this.price = price;
    }

    private void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    private void setNameForDelivery(String nameForDelivery) {
        this.nameForDelivery = nameForDelivery;
    }

    private void setPhoneNumberForDelivery(String phoneNumberForDelivery) {
        this.phoneNumberForDelivery = phoneNumberForDelivery;
    }

}
