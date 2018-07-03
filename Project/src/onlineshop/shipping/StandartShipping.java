package onlineshop.shipping;

public class StandartShipping extends Shipping {

    final static String DELIVERY_TIME="0-1 Week";
    @Override
    public void setType(ShippingType type) {
        type= ShippingType.STANDART;
        super.setType(type);
    }

    @Override
    public void setDeliveryTime(String deliveryTime) {
        deliveryTime=DELIVERY_TIME;
        super.setDeliveryTime(deliveryTime);
    }


    @Override
    public void setPrice() {
        super.price=price;
    }
}
