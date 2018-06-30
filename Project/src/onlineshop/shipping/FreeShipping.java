package onlineshop.shipping;
<<<<<<< HEAD
=======

>>>>>>> db72fcb445bd2aca4639e158dfb83f18ba2e8b82

public class FreeShipping extends Shipping {
    final static String DELIVERY_TIME="1-2 Weeks";
    final static double FREE_SHIPPING_PRICE = 0.0;
    @Override
    public void setType(ShippingType type) {
        type = ShippingType.FREE;
        super.setType(type);
    }

    @Override
    public void setDeliveryTime(String deliveryTime) {
        deliveryTime = DELIVERY_TIME;
        super.setDeliveryTime(deliveryTime);
    }

    @Override
    public void setShippingAddress(String shippingAddress) {
        super.setShippingAddress(shippingAddress);
    }

    @Override
    public void setPrice(double price) {
        super.price = FREE_SHIPPING_PRICE;
    }


}
