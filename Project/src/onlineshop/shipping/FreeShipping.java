package onlineshop.shipping;


public class FreeShipping extends Shipping {

    private final static String DELIVERY_TIME = "1-2 weeks";
    private final static double PRICE_OF_FREE_SHIPPING = 0.0;

    public FreeShipping(String nameForDelivery, String phoneNumberForDelivery, String shippingAddress) {
        super(nameForDelivery, phoneNumberForDelivery, shippingAddress, PRICE_OF_FREE_SHIPPING, DELIVERY_TIME);
        setTypeOfShipping();
    }

    @Override
    void setTypeOfShipping() {
        this.type = ShippingType.FREE;
    }

}
