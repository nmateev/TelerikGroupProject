package onlineshop.users;

import onlineshop.order.Order;
import onlineshop.product.Product;

public class VipUser extends User {
    public final double SILVER_VIP_USER_DISCOUNT = 0.05;
    public final double GOLD_VIP_USER_DISCOUNT = 0.10;
    public final double PLATINUM_VIP_USER_DISCOUNT = 0.15;

    private VipUserType type;

    protected VipUser(String userName, String address, String phone, String password, Cart cart) {
        super(userName, address, phone, password, new Cart());
    }

    public VipUserType getVipType() {
        return type;
    }

    private void setType(VipUserType type) {
        this.type = type;
    }
}