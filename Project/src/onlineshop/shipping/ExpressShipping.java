package onlineshop.shipping;

public class ExpressShipping extends Shipping {

    private final static String DELIVERY_TIME = "2-3 days";
    private final static double PRICE_OF_EXPRESS_SHIPPING = 10.0;

    public ExpressShipping(String nameForDelivery, String phoneNumberForDelivery, String shippingAddress) {
        super(nameForDelivery, phoneNumberForDelivery, shippingAddress, PRICE_OF_EXPRESS_SHIPPING, DELIVERY_TIME);
        setTypeOfShipping();
    }


    @Override
    void setTypeOfShipping() {
        this.type = ShippingType.EXPRESS;
    }
}
