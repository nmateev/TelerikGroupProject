package onlineshop.shipping;

public class StandardShipping extends Shipping {

    private final static String DELIVERY_TIME = "5-7 days";
    private final static double PRICE_OF_STANDARD_SHIPPING = 5.0;

    public StandardShipping(String nameForDelivery, String phoneNumberForDelivery, String shippingAddress) {
        super(nameForDelivery, phoneNumberForDelivery, shippingAddress, PRICE_OF_STANDARD_SHIPPING, DELIVERY_TIME);
        setTypeOfShipping();
    }


    @Override
    void setTypeOfShipping() {
        this.type = ShippingType.STANDARD;
    }
}
