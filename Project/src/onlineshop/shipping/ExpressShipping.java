package onlineshop.shipping;

public class ExpressShipping extends Shipping {
    final static String DELIVERY_TIME="2-3 Days";
    @Override
    public void setType(ShippingType type) {
        type= ShippingType.EXPRESS;
        super.setType(type);
    }

    @Override
    public void setDeliveryTime(String deliveryTime) {
        deliveryTime=DELIVERY_TIME;
        super.setDeliveryTime(deliveryTime);
    }

    @Override
    public void setPrice(double price) {

    }
}
